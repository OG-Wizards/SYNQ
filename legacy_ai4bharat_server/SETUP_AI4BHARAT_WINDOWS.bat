@echo off
setlocal EnableExtensions
cd /d "%~dp0"

echo ============================================================
echo iTantra - AI4Bharat local engine setup
 echo ============================================================

if not exist .venv\Scripts\python.exe (
  python -m venv .venv
)
call .venv\Scripts\activate.bat
python -m pip install --upgrade pip
pip install -r requirements.txt

echo.
echo Next:
echo 1. Get the official IndicTrans2 repository/checkpoint.
echo 2. Get the official Indic-TTS checkpoints for the languages you need.
echo 3. Set INDICTRANS2_DIR, INDICTRANS2_CKPT and TTS_ROOT.
echo 4. Run START_AI4BHARAT_WINDOWS.bat
pause
