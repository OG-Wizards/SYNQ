import base64
import os
import subprocess
import sys
import tempfile
from pathlib import Path

from fastapi import FastAPI, HTTPException
from pydantic import BaseModel

app = FastAPI(title="iTantra AI4Bharat Engine", version="1.0")

LANG = {
    "en": "eng_Latn", "mr": "mar_Deva", "hi": "hin_Deva", "gu": "guj_Gujr",
    "bn": "ben_Beng", "ta": "tam_Taml", "te": "tel_Telu", "kn": "kan_Knda"
}

class TranslateRequest(BaseModel):
    text: str
    source: str
    target: str

class TtsRequest(BaseModel):
    text: str
    language: str


def translate_with_indictrans2(text: str, source: str, target: str) -> str:
    root = os.environ.get("INDICTRANS2_DIR")
    ckpt = os.environ.get("INDICTRANS2_CKPT")
    if not root or not ckpt:
        raise RuntimeError("Set INDICTRANS2_DIR and INDICTRANS2_CKPT")
    src = LANG.get(source, source)
    tgt = LANG.get(target, target)
    old = os.getcwd()
    try:
        os.chdir(root)
        sys.path.insert(0, root)
        from inference.engine import Model
        model = Model(ckpt, model_type="ctranslate2")
        return model.translate_paragraph(text, src, tgt)
    finally:
        os.chdir(old)


def tts_with_indic_tts(text: str, language: str) -> bytes:
    root = os.environ.get("TTS_ROOT")
    if not root:
        raise RuntimeError("Set TTS_ROOT to the folder containing Indic-TTS language checkpoints")
    lang = {"mr":"mr", "hi":"hi", "gu":"gu", "bn":"bn", "ta":"ta", "te":"te", "kn":"kn", "en":"en"}.get(language, language)
    model_dir = Path(root) / lang
    model_path = model_dir / "fastpitch" / "best_model.pth"
    config_path = model_dir / "config.json"
    vocoder_path = model_dir / "hifigan" / "best_model.pth"
    vocoder_config = model_dir / "hifigan" / "config.json"
    for p in (model_path, config_path, vocoder_path, vocoder_config):
        if not p.exists():
            raise RuntimeError(f"Missing Indic-TTS checkpoint file: {p}")
    with tempfile.NamedTemporaryFile(suffix=".wav", delete=False) as f:
        out = f.name
    try:
        cmd = [
            os.environ.get("TTS_PYTHON", sys.executable), "-m", "TTS.bin.synthesize",
            "--text", text,
            "--model_path", str(model_path),
            "--config_path", str(config_path),
            "--vocoder_path", str(vocoder_path),
            "--vocoder_config_path", str(vocoder_config),
            "--out_path", out,
        ]
        result = subprocess.run(cmd, capture_output=True, text=True, cwd=str(root))
        if result.returncode != 0:
            raise RuntimeError(result.stderr[-2000:] or result.stdout[-2000:])
        return Path(out).read_bytes()
    finally:
        try:
            Path(out).unlink(missing_ok=True)
        except Exception:
            pass

@app.get("/health")
def health():
    return {"ok": True, "translation": bool(os.environ.get("INDICTRANS2_DIR") and os.environ.get("INDICTRANS2_CKPT")), "tts": bool(os.environ.get("TTS_ROOT"))}

@app.post("/translate")
def translate(req: TranslateRequest):
    if not req.text.strip():
        return {"text": ""}
    if req.source == req.target:
        return {"text": req.text}
    try:
        return {"text": translate_with_indictrans2(req.text, req.source, req.target)}
    except Exception as exc:
        raise HTTPException(status_code=503, detail=str(exc))

@app.post("/tts")
def tts(req: TtsRequest):
    if not req.text.strip():
        return {"audio_base64": ""}
    try:
        audio = tts_with_indic_tts(req.text, req.language)
        return {"audio_base64": base64.b64encode(audio).decode("ascii")}
    except Exception as exc:
        raise HTTPException(status_code=503, detail=str(exc))

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=int(os.environ.get("PORT", "8765")))
