/* =========================================================
   iTantra Web Presentation Showcase - Interactive Engine
   ========================================================= */

// --- 10-LANGUAGE LOCALIZATION DICTIONARY ---
const I18N = {
  mr: {
    name: "मराठी (Marathi)",
    appTagline: "ऑफलाइन संप्रेषण नेटवर्क",
    modeWalkie: "वॉकी",
    modeCall: "खाजगी कॉल",
    modeGroups: "गट",
    yourLanguage: "तुमची भाषा",
    change: "बदला",
    holdToTalk: "बोलण्यासाठी धरून ठेवा",
    listeningActive: "ऐकत आहे…",
    recognizedSpeech: "ओळखलेले संभाषण",
    sampleTranscript: "आम्ही सुरक्षित ठिकाणी पोहोचलो आहोत.",
    standby: "ऑफलाइन तयार",
    callTitle: "खाजगी वाय-फाय कॉल",
    callSubtitle: "सिम किंवा इंटरनेटशिवाय रिअल-टाइम १६kHz PCM ऑडिओ",
    selectedPeerLabel: "निवडलेला पीयर:",
    startCall: "खाजगी कॉल सुरू करा",
    calling: "कॉल करत आहे…",
    callInProgress: "कॉल चालू आहे",
    liveAudioWith: "सोबत थेट ऑडिओ प्रवाह:",
    endCall: "कॉल समाप्त करा",
    groupsTitle: "गट",
    groupsSubtitle: "पथके · सुरक्षा · प्रतिसाद दल",
    newGroupName: "नवीन गटाचे नाव",
    createGroup: "+ तयार करा",
    sendGroupAlert: "गट सूचना पाठवा",
    meshLink: "मेश लिंक",
    meshSubtitle: "थेट पीअर-टू-पीअर नेटवर्क",
    scan: "स्कॅन",
    meshConnected: "कनेक्ट झाले",
    devicesDiscovered: "3 डिव्हाइस सापडले",
    discoveredPeers: "सापडलेले पीअर्स",
    messagesTitle: "संदेश",
    messagesSubtitle: "संभाषण इतिहास",
    clear: "साफ करा",
    sosTitle: "आपत्कालीन SOS",
    sosSubtitle: "ऑफलाइन मेशवर उच्च-प्राधान्य संकट सिग्नल पाठवा",
    alertCyclone: "चक्रीवादळ इशारा (Cyclone Alert)",
    alertFlood: "अचानक पूर (Flash Flood)",
    alertMedical: "वैद्यकीय आणीबाणी (Medical Emergency)",
    alertComms: "संपर्क तुटला (Comms Blackout)",
    broadcastSos: "आपत्कालीन SOS प्रसारित करा",
    settingsTitle: "सेटिंग्ज",
    settingsSubtitle: "iTantra संरचना",
    darkMode: "डार्क मोड",
    darkAppearanceEnabled: "डार्क मोड चालू आहे",
    language: "भाषा",
    translationModel: "भाषांतर मॉडेल",
    modelReady: "तयार (ऑन-डिव्हाइस)",
    realtimeDashboard: "रिअल-टाइम डॅशबोर्ड",
    open: "उघडा",
    dashSubtitle: "STT · भाषांतर · TTS · E2E · त्रुटी दर · RAM · CPU",
    errorRate: "त्रुटी दर (Error Rate)",
    errorDrops: "० पॅकेट गळती (उत्कृष्ट ०.००%)",
    packetLoss: "पॅकेट लॉस",
    meshHops: "थेट १-हॉप P2P लिंक",
    offlineAi: "ऑफलाइन AI",
    offlineAi1: "• ऑन-डिव्हाइस अँड्रॉइड आवाज ओळख",
    offlineAi2: "• गुगल ML किट ऑन-डिव्हाइस भाषांतर",
    offlineAi3: "• नेटिव अँड्रॉइड टेक्स्ट-टू-स्पीच",
    offlineAiFooter: "सर्व मुख्य संप्रेषण इंटरनेट किंवा बाह्य सर्व्हरशिवाय थेट स्थानिक वाय-फायवर चालते.",
    tabWalkie: "वॉकी",
    tabMesh: "मेश",
    tabMessages: "संदेश",
    tabSos: "SOS",
    tabSettings: "सेटिंग्ज",
    you: "तुम्ही",
    peer: "समोरचा",
    sos: "SOS"
  },
  hi: {
    name: "हिन्दी (Hindi)",
    appTagline: "ऑफलाइन संचार नेटवर्क",
    modeWalkie: "वॉकी",
    modeCall: "निजी कॉल",
    modeGroups: "समूह",
    yourLanguage: "आपकी भाषा",
    change: "बदलें",
    holdToTalk: "बोलने के लिए दबाकर रखें",
    listeningActive: "सुन रहा है…",
    recognizedSpeech: "पहचाना गया भाषण",
    sampleTranscript: "हम सुरक्षित स्थान पर पहुंच गए हैं।",
    standby: "ऑफलाइन तैयार",
    callTitle: "निजी वाई-फाई कॉल",
    callSubtitle: "सिम या इंटरनेट के बिना रीयल-टाइम 16kHz PCM ऑडियो",
    selectedPeerLabel: "चयनित पीयर:",
    startCall: "निजी कॉल शुरू करें",
    calling: "कॉल कर रहे हैं…",
    callInProgress: "कॉल जारी है",
    liveAudioWith: "के साथ लाइव ऑडियो स्ट्रीम:",
    endCall: "कॉल समाप्त करें",
    groupsTitle: "समूह",
    groupsSubtitle: "टीमें · सुरक्षा · प्रतिक्रिया इकाइयाँ",
    newGroupName: "नया समूह नाम",
    createGroup: "+ बनाएं",
    sendGroupAlert: "समूह चेतावनी भेजें",
    meshLink: "मेश लिंक",
    meshSubtitle: "सीधा पीयर-टू-पीअर नेटवर्क",
    scan: "स्कैन",
    meshConnected: "जुड़ा हुआ",
    devicesDiscovered: "3 उपकरण मिले",
    discoveredPeers: "खोजे गए पीयर",
    messagesTitle: "संदेश",
    messagesSubtitle: "संचार इतिहास",
    clear: "साफ़ करें",
    sosTitle: "आपातकालीन SOS",
    sosSubtitle: "ऑफलाइन मेश पर उच्च-प्राथमिकता संकट संकेत प्रसारित करें",
    alertCyclone: "चक्रवात चेतावनी (Cyclone Alert)",
    alertFlood: "आकस्मिक बाढ़ (Flash Flood)",
    alertMedical: "चिकित्सा आपातकाल (Medical Emergency)",
    alertComms: "संचार ब्लैकआउट (Comms Blackout)",
    broadcastSos: "आपातकालीन SOS प्रसारित करें",
    settingsTitle: "सेटिंग्स",
    settingsSubtitle: "iTantra कॉन्फ़िगरेशन",
    darkMode: "डार्क मोड",
    darkAppearanceEnabled: "डार्क मोड सक्षम है",
    language: "भाषा",
    translationModel: "अनुवाद मॉडल",
    modelReady: "तैयार (ऑन-डिवाइस)",
    realtimeDashboard: "रीयल-टाइम डैशबोर्ड",
    open: "खोलें",
    dashSubtitle: "STT · अनुवाद · TTS · E2E · त्रुटि दर · RAM · CPU",
    errorRate: "त्रुटि दर (Error Rate)",
    errorDrops: "० पैकेट ड्रॉप (इष्टतम ०.००%)",
    packetLoss: "पैकेट हानि",
    meshHops: "सीधा 1-हॉप P2P लिंक",
    offlineAi: "ऑफलाइन AI",
    offlineAi1: "• ऑन-डिवाइस एंड्रॉइड स्पीच रिकग्निशन",
    offlineAi2: "• गूगल ML किट ऑन-डिवाइस अनुवाद",
    offlineAi3: "• नेटिव एंड्रॉइड टेक्स्ट-टू-स्पीच",
    offlineAiFooter: "सभी मुख्य संचार इंटरनेट या बाहरी सर्वर के बिना स्थानीय वाई-फाई पर सीधे पीयर-टू-पीअर चलता है।",
    tabWalkie: "वॉकी",
    tabMesh: "मेश",
    tabMessages: "संदेश",
    tabSos: "SOS",
    tabSettings: "सेटिंग्स",
    you: "आप",
    peer: "पीयर",
    sos: "SOS"
  },
  gu: {
    name: "ગુજરાતી (Gujarati)",
    appTagline: "ઑફલાઇન સંચાર નેટવર્ક",
    modeWalkie: "વૉકી",
    modeCall: "ખાનગી કૉલ",
    modeGroups: "જૂથો",
    yourLanguage: "તમારી ભાષા",
    change: "બદલો",
    holdToTalk: "બોલવા માટે દબાવી રાખો",
    listeningActive: "સાંભળી રહ્યું છે…",
    recognizedSpeech: "ઓળખાયેલ ભાષણ",
    sampleTranscript: "અમે સલામત સ્થળે પહોંચી ગયા છીએ.",
    standby: "ઑફલાઇન તૈયાર",
    callTitle: "ખાનગી વાઇ-ફાઇ કૉલ",
    callSubtitle: "સિમ અથવા ઇન્ટરનેટ વિના રીઅલ-ટાઇમ 16kHz PCM ઑડિઓ",
    selectedPeerLabel: "પસંદ કરેલ પીઅર:",
    startCall: "ખાનગી કૉલ શરૂ કરો",
    calling: "કૉલ કરી રહ્યા છીએ…",
    callInProgress: "કૉલ ચાલુ છે",
    liveAudioWith: "સાથે લાઇવ ઑડિઓ સ્ટ્રીમ:",
    endCall: "કૉલ સમાપ્ત કરો",
    groupsTitle: "જૂથો",
    groupsSubtitle: "ટીમો · સુરક્ષા · પ્રતિભાવ ટીમો",
    newGroupName: "નવું જૂથ નામ",
    createGroup: "+ બનાવો",
    sendGroupAlert: "જૂથ ચેતવણી મોકલો",
    meshLink: "મેશ લિંક",
    meshSubtitle: "સીધું પીઅર-ટુ-પીઅર નેટવર્ક",
    scan: "સ્કેન",
    meshConnected: "જોડાયેલ",
    devicesDiscovered: "3 ઉપકરણો મળ્યાં",
    discoveredPeers: "મળેલા પીઅર્સ",
    messagesTitle: "સંદેશાઓ",
    messagesSubtitle: "સંચાર ઇતિહાસ",
    clear: "સાફ કરો",
    sosTitle: "ઇમરજન્સી SOS",
    sosSubtitle: "ઑફલાઇન મેશ પર ઉચ્ચ પ્રાથમિકતા કટોકટી સંકેત પ્રસારિત કરો",
    alertCyclone: "વાવાઝોડાની ચેતવણી (Cyclone Alert)",
    alertFlood: "અચાનક પૂર (Flash Flood)",
    alertMedical: "તબીબી કટોકટી (Medical Emergency)",
    alertComms: "સંચાર બ્લૅકઆઉટ (Comms Blackout)",
    broadcastSos: "ઇમરજન્સી SOS પ્રસારિત કરો",
    settingsTitle: "સેટિંગ્સ",
    settingsSubtitle: "iTantra રૂપરેખાંકન",
    darkMode: "ડાર્ક મોડ",
    darkAppearanceEnabled: "ડાર્ક મોડ ચાલુ છે",
    language: "ભાષા",
    translationModel: "અનુવાદ મોડલ",
    modelReady: "તૈયાર (ઑન-ડિવાઇસ)",
    realtimeDashboard: "રીઅલ-ટાઇમ ડેશબોર્ડ",
    open: "ખોલો",
    dashSubtitle: "STT · અનુવાદ · TTS · E2E · ભૂલ દર · RAM · CPU",
    errorRate: "ભૂલ દર (Error Rate)",
    errorDrops: "૦ પેકેટ ડ્રોપ (શ્રેષ્ઠ ૦.૦૦%)",
    packetLoss: "પેકેટ નુકસાન",
    meshHops: "સીધી 1-હોપ P2P લિંક",
    offlineAi: "ઑફલાઇન AI",
    offlineAi1: "• ઑન-ડિવાઇસ એન્ડ્રોઇડ સ્પીચ ઓળખ",
    offlineAi2: "• ગૂગલ ML કિટ ઑન-ડિવાઇસ અનુવાદ",
    offlineAi3: "• નેટિવ એન્ડ્રોઇડ ટેક્સ્ટ-ટુ-સ્પીચ",
    offlineAiFooter: "તમામ મુખ્ય સંચાર ઇન્ટરનેટ અથવા બાહ્ય સર્વર્સ વિના સ્થાનિક Wi-Fi પર સીધા પીઅર-ટુ-પીઅર ચાલે છે.",
    tabWalkie: "વૉકી",
    tabMesh: "મેશ",
    tabMessages: "સંદેશાઓ",
    tabSos: "SOS",
    tabSettings: "સેટિંગ્સ",
    you: "તમે",
    peer: "પીયર",
    sos: "SOS"
  },
  bn: {
    name: "বাংলা (Bengali)",
    appTagline: "অফলাইন যোগাযোগ নেটওয়ার্ক",
    modeWalkie: "ওয়াকি",
    modeCall: "ব্যক্তিগত কল",
    modeGroups: "গ্রুপ",
    yourLanguage: "আপনার ভাষা",
    change: "পরিবর্তন",
    holdToTalk: "কথা বলতে ধরে রাখুন",
    listeningActive: "শুনছে…",
    recognizedSpeech: "শনাক্তকৃত বক্তব্য",
    sampleTranscript: "আমরা নিরাপদ স্থানে পৌঁছেছি।",
    standby: "অফলাইনে প্রস্তুত",
    callTitle: "ব্যক্তিগত ওয়াই-ফাই কল",
    callSubtitle: "সিম বা ইন্টারনেট ছাড়া রিয়েল-টাইম ১৬kHz PCM অডিও",
    selectedPeerLabel: "নির্বাচিত পিয়ার:",
    startCall: "ব্যক্তিগত কল শুরু করুন",
    calling: "কল করা হচ্ছে…",
    callInProgress: "কল চলছে",
    liveAudioWith: "এর সাথে লাইভ অডিও স্ট্রিম:",
    endCall: "কল শেষ করুন",
    groupsTitle: "গ্রুপ",
    groupsSubtitle: "দল · নিরাপত্তা · প্রতিক্রিয়া দল",
    newGroupName: "নতুন গ্রুপের নাম",
    createGroup: "+ তৈরি করুন",
    sendGroupAlert: "গ্রুপ সতর্কতা পাঠান",
    meshLink: "মেশ লিঙ্ক",
    meshSubtitle: "সরাসরি পিয়ার-টু-পিয়ার নেটওয়ার্ক",
    scan: "স্ক্যান",
    meshConnected: "সংযুক্ত",
    devicesDiscovered: "3 ডিভাইস পাওয়া গেছে",
    discoveredPeers: "আবিষ্কৃত পিয়ার্স",
    messagesTitle: "বার্তা",
    messagesSubtitle: "যোগাযোগের ইতিহাস",
    clear: "মুছুন",
    sosTitle: "জরুরি SOS",
    sosSubtitle: "অফলাইন মেশে উচ্চ-অগ্রাধিকারের বিপদ সংকেত সম্প্রচার করুন",
    alertCyclone: "ঘূর্ণিঝড় সতর্কতা (Cyclone Alert)",
    alertFlood: "আকস্মিক বন্যা (Flash Flood)",
    alertMedical: "চিকিৎসা জরুরি অবস্থা (Medical Emergency)",
    alertComms: "যোগাযোগ বিচ্ছিন্ন (Comms Blackout)",
    broadcastSos: "জরুরি SOS সম্প্রচার করুন",
    settingsTitle: "সেটিংস",
    settingsSubtitle: "iTantra কনফিগারেশন",
    darkMode: "ডার্ক মোড",
    darkAppearanceEnabled: "ডার্ক মোড সক্ষম",
    language: "ভাষা",
    translationModel: "অনুবাদ মডেল",
    modelReady: "প্রস্তুত (অন-ডিভাইস)",
    realtimeDashboard: "রিয়েল-টাইম ড্যাশবোর্ড",
    open: "খুলুন",
    dashSubtitle: "STT · অনুবাদ · TTS · E2E · ত্রুটি হার · RAM · CPU",
    errorRate: "ত্রুটি হার (Error Rate)",
    errorDrops: "০ প্যাকেট ড্রপ (অনুকূল ০.০০%)",
    packetLoss: "প্যাকেট ক্ষতি",
    meshHops: "সরাসরি ১-হপ P2P লিঙ্ক",
    offlineAi: "অফলাইন AI",
    offlineAi1: "• অন-ডিভাইস অ্যান্ড্রয়েড স্পিচ রিকগনিশন",
    offlineAi2: "• গুগল ML কিট অন-ডিভাইস অনুবাদ",
    offlineAi3: "• নেটিভ অ্যান্ড্রয়েড টেক্সট-টু-স্পিচ",
    offlineAiFooter: "সমস্ত যোগাযোগ ইন্টারনেট বা বহিরাগত সার্ভার ছাড়াই স্থানীয় ওয়াই-ফাইয়ের মাধ্যমে সরাসরি পিয়ার-টু-পিয়ার চলে।",
    tabWalkie: "ওয়াকি",
    tabMesh: "মেশ",
    tabMessages: "বার্তা",
    tabSos: "SOS",
    tabSettings: "সেটিংস",
    you: "আপনি",
    peer: "পিয়ার",
    sos: "SOS"
  },
  ta: {
    name: "தமிழ் (Tamil)",
    appTagline: "ஆஃப்லைன் தொடர்பு நெட்வொர்க்",
    modeWalkie: "வாக்கி",
    modeCall: "தனிப்பட்ட அழைப்பு",
    modeGroups: "குழுக்கள்",
    yourLanguage: "உங்கள் மொழி",
    change: "மாற்று",
    holdToTalk: "பேச அழுத்திப் பிடிக்கவும்",
    listeningActive: "கேட்கிறது…",
    recognizedSpeech: "அடையாளம் காணப்பட்ட பேச்சு",
    sampleTranscript: "நாங்கள் பாதுகாப்பான இடத்தை அடைந்துவிட்டோம்.",
    standby: "ஆஃப்லைன் தயார்",
    callTitle: "தனிப்பட்ட வைஃபை அழைப்பு",
    callSubtitle: "சிம் அல்லது இணையம் இல்லாமல் நிகழ்நேர 16kHz PCM ஆடியோ",
    selectedPeerLabel: "தேர்ந்தெடுக்கப்பட்ட பியர்:",
    startCall: "தனிப்பட்ட அழைப்பைத் தொடங்கவும்",
    calling: "அழைக்கிறது…",
    callInProgress: "அழைப்பு செயலில் உள்ளது",
    liveAudioWith: "உடன் நேரலை ஆடியோ ஸ்ட்ரீம்:",
    endCall: "அழைப்பை முடிக்கவும்",
    groupsTitle: "குழுக்கள்",
    groupsSubtitle: "அணிகள் · பாதுகாப்பு · மீட்புக் குழுக்கள்",
    newGroupName: "புதிய குழு பெயர்",
    createGroup: "+ உருவாக்கு",
    sendGroupAlert: "குழு எச்சரிக்கை அனுப்புக",
    meshLink: "மெஷ் இணைப்பு",
    meshSubtitle: "நேரடி பியர்-டு-பியர் நெட்வொர்க்",
    scan: "ஸ்கேன்",
    meshConnected: "இணைக்கப்பட்டது",
    devicesDiscovered: "3 சாதனங்கள் கண்டறியப்பட்டன",
    discoveredPeers: "கண்டறியப்பட்ட பியர்ஸ்",
    messagesTitle: "செய்திகள்",
    messagesSubtitle: "தொடர்பு வரலாறு",
    clear: "அழி",
    sosTitle: "அவசர SOS",
    sosSubtitle: "ஆஃப்லைன் மெஷில் உயர் முன்னுரிமை ஆபத்து சிக்னலை ஒளிபரப்பவும்",
    alertCyclone: "புயல் எச்சரிக்கை (Cyclone Alert)",
    alertFlood: "திடீர் வெள்ளம் (Flash Flood)",
    alertMedical: "மருத்துவ அவசரநிலை (Medical Emergency)",
    alertComms: "தொடர்பு துண்டிப்பு (Comms Blackout)",
    broadcastSos: "அவசர SOS ஒலிபரப்பவும்",
    settingsTitle: "அமைப்புகள்",
    settingsSubtitle: "iTantra கட்டமைப்பு",
    darkMode: "இருண்ட பயன்முறை",
    darkAppearanceEnabled: "இருண்ட தோற்றம் இயக்கப்பட்டது",
    language: "மொழி",
    translationModel: "மொழிபெயர்ப்பு மாதிரி",
    modelReady: "தயார் (ஆன்-டிவைஸ்)",
    realtimeDashboard: "நிகழ்நேர டாஷ்போர்டு",
    open: "திற",
    dashSubtitle: "STT · மொழிபெயர்ப்பு · TTS · E2E · பிழை விகிதம் · RAM · CPU",
    errorRate: "பிழை விகிதம் (Error Rate)",
    errorDrops: "0 பாக்கெட் இழப்பு (0.00%)",
    packetLoss: "பாக்கெட் இழப்பு",
    meshHops: "நேரடி 1-ஹாப் P2P",
    offlineAi: "ஆஃப்லைன் AI",
    offlineAi1: "• ஆன்-டிவைஸ் ஆண்ட்ராய்டு பேச்சு அங்கீகாரம்",
    offlineAi2: "• கூகிள் ML கிட் ஆன்-டிவைஸ் மொழிபெயர்ப்பு",
    offlineAi3: "• நேட்டிவ் ஆண்ட்ராய்டு உரையிலிருந்து பேச்சு",
    offlineAiFooter: "அனைத்து தகவல் தொடர்புகளும் இணையம் அல்லது வெளிப்புற சேவையகங்கள் இல்லாமல் உள்ளூர் வைஃபை வழியாக நேரடியாக பியர்-டு-பியர் இயங்குகிறது.",
    tabWalkie: "வாக்கி",
    tabMesh: "மெஷ்",
    tabMessages: "செய்திகள்",
    tabSos: "SOS",
    tabSettings: "அமைப்புகள்",
    you: "நீங்கள்",
    peer: "பியர்",
    sos: "SOS"
  },
  te: {
    name: "తెలుగు (Telugu)",
    appTagline: "ఆఫ్‌లైన్ కమ్యూనికేషన్ నెట్‌వర్క్",
    modeWalkie: "వాకీ",
    modeCall: "ప్రైవేట్ కాల్",
    modeGroups: "సమూహాలు",
    yourLanguage: "మీ భాష",
    change: "మార్చు",
    holdToTalk: "మాట్లాడటానికి నొక్కి పట్టుకోండి",
    listeningActive: "వింటోంది…",
    recognizedSpeech: "గుర్తించిన మాటలు",
    sampleTranscript: "మేము సురక్షితమైన ప్రదేశానికి చేరుకున్నాము.",
    standby: "ఆఫ్‌లైన్ సిద్ధం",
    callTitle: "ప్రైవేట్ వై-ఫై కాల్",
    callSubtitle: "సిమ్ లేదా ఇంటర్నెట్ లేకుండా రియల్-టైమ్ 16kHz PCM ఆడియో",
    selectedPeerLabel: "ఎంచుకున్న పీర్:",
    startCall: "ప్రైవేట్ కాల్ ప్రారంభించండి",
    calling: "కాల్ చేస్తోంది…",
    callInProgress: "కాల్ కొనసాగుతోంది",
    liveAudioWith: "తో లైవ్ ఆడియో స్ట్రీమ్:",
    endCall: "కాల్ ముగించండి",
    groupsTitle: "సమూహాలు",
    groupsSubtitle: "జట్లు · భద్రత · ప్రతిస్పందన దళాలు",
    newGroupName: "కొత్త సమూహం పేరు",
    createGroup: "+ సృష్టించు",
    sendGroupAlert: "గ్రూప్ హెచ్చరిక పంపండి",
    meshLink: "మెష్ లింక్",
    meshSubtitle: "ప్రత్యక్ష పీర్-టు-పీర్ నెట్‌వర్క్",
    scan: "స్కాన్",
    meshConnected: "కనెక్ట్ చేయబడింది",
    devicesDiscovered: "3 పరికరాలు కనుగొనబడ్డాయి",
    discoveredPeers: "కనుగొనబడిన పీర్లు",
    messagesTitle: "సందేశాలు",
    messagesSubtitle: "కమ్యూనికేషన్ చరిత్ర",
    clear: "క్లియర్",
    sosTitle: "అత్యవసర SOS",
    sosSubtitle: "ఆఫ్‌లైన్ మెష్‌లో అధిక-ప్రాధాన్యత విపత్తు సిగ్నల్‌ను ప్రసారం చేయండి",
    alertCyclone: "తుఫాను హెచ్చరిక (Cyclone Alert)",
    alertFlood: "ఆకస్మిక వరద (Flash Flood)",
    alertMedical: "వైద్య అత్యవసర పరిస్థితి (Medical Emergency)",
    alertComms: "కమ్యూనికేషన్ బ్లాక్‌అవుట్ (Comms Blackout)",
    broadcastSos: "అత్యవసర SOS ప్రసారం చేయండి",
    settingsTitle: "సెట్టింగులు",
    settingsSubtitle: "iTantra కాన్ఫిగరేషన్",
    darkMode: "డార్క్ మోడ్",
    darkAppearanceEnabled: "డార్క్ ప్రదర్శన ప్రారంభించబడింది",
    language: "భాష",
    translationModel: "అనువాద నమూనా",
    modelReady: "సిద్ధం (ఆన్-డివైస్)",
    realtimeDashboard: "రియల్-టైమ్ డ్యాష్‌బోర్డ్",
    open: "తెరవండి",
    dashSubtitle: "STT · అనువాదం · TTS · E2E · లోపం రేటు · RAM · CPU",
    errorRate: "లోపం రేటు (Error Rate)",
    errorDrops: "0 ప్యాకెట్ నష్టం (0.00%)",
    packetLoss: "ప్యాకెట్ నష్టం",
    meshHops: "డైరెక్ట్ 1-హాప్ P2P",
    offlineAi: "ఆఫ్‌లైన్ AI",
    offlineAi1: "• ఆన్-డివైస్ ఆండ్రాయిడ్ స్పీచ్ రికగ్నిషన్",
    offlineAi2: "• గూగుల్ ML కిట్ ఆన్-డివైస్ అనువాదం",
    offlineAi3: "• స్థానిక ఆండ్రాయిడ్ టెక్స్ట్-టు-స్పీచ్",
    offlineAiFooter: "అన్ని కమ్యూనికేషన్లు ఇంటర్నెట్ లేదా బాహ్య సర్వర్లు లేకుండా స్థానిక వై-ఫై ద్వారా నేరుగా పీర్-టు-పీర్ నడుస్తాయి.",
    tabWalkie: "వాకీ",
    tabMesh: "మెష్",
    tabMessages: "సందేశాలు",
    tabSos: "SOS",
    tabSettings: "సెట్టింగులు",
    you: "మీరు",
    peer: "పీర్",
    sos: "SOS"
  },
  kn: {
    name: "ಕನ್ನಡ (Kannada)",
    appTagline: "ಆಫ್‌ಲೈನ್ ಸಂವಹನ ಜಾಲ",
    modeWalkie: "ವಾಕಿ",
    modeCall: "ಖಾಸಗಿ ಕರೆ",
    modeGroups: "ಗುಂಪುಗಳು",
    yourLanguage: "ನಿಮ್ಮ ಭಾಷೆ",
    change: "ಬದಲಾಯಿಸಿ",
    holdToTalk: "ಮಾತನಾಡಲು ಒತ್ತಿ ಹಿಡಿಯಿರಿ",
    listeningActive: "ಕೇಳುತ್ತಿದೆ…",
    recognizedSpeech: "ಗುರುತಿಸಲಾದ ಮಾತು",
    sampleTranscript: "ನಾವು ಸುರಕ್ಷಿತ ಸ್ಥಳವನ್ನು ತಲುಪಿದ್ದೇವೆ.",
    standby: "ಆಫ್‌ಲೈನ್ ಸಿದ್ಧ",
    callTitle: "ಖಾಸಗಿ ವೈ-ಫೈ ಕರೆ",
    callSubtitle: "ಸಿಮ್ ಅಥವಾ ಇಂಟರ್ನೆಟ್ ಇಲ್ಲದೆ ನೈಜ-ಸಮಯದ 16kHz PCM ಆಡಿಯೋ",
    selectedPeerLabel: "ಆಯ್ಕೆಮಾಡಿದ ಪೀರ್:",
    startCall: "ಖಾಸಗಿ ಕರೆ ಪ್ರಾರಂಭಿಸಿ",
    calling: "ಕರೆ ಮಾಡಲಾಗುತ್ತಿದೆ…",
    callInProgress: "ಕರೆ ಪ್ರಗತಿಯಲ್ಲಿದೆ",
    liveAudioWith: "ಜೊತೆಗೆ ಲೈವ್ ಆಡಿಯೋ ಸ್ಟ್ರೀಮ್:",
    endCall: "ಕರೆ ಮುಗಿಸಿ",
    groupsTitle: "ಗುಂಪುಗಳು",
    groupsSubtitle: "ತಂಡಗಳು · ಭದ್ರತೆ · ಪ್ರತಿಕ್ರಿಯಾ ಪಡೆಗಳು",
    newGroupName: "ಹೊಸ ಗುಂಪಿನ ಹೆಸರು",
    createGroup: "+ ರಚಿಸಿ",
    sendGroupAlert: "ಗುಂಪು ಎಚ್ಚರಿಕೆ ಕಳುಹಿಸಿ",
    meshLink: "ಮೆಶ್ ಲಿಂಕ್",
    meshSubtitle: "ನೇರ ಪೀರ್-ಟು-ಪೀರ್ ಜಾಲ",
    scan: "ಸ್ಕ್ಯಾನ್",
    meshConnected: "ಸಂಪರ್ಕಗೊಂಡಿದೆ",
    devicesDiscovered: "3 ಸಾಧನಗಳು ಪತ್ತೆಯಾಗಿವೆ",
    discoveredPeers: "ಪತ್ತೆಯಾದ ಪೀರ್‌ಗಳು",
    messagesTitle: "ಸಂದೇಶಗಳು",
    messagesSubtitle: "ಸಂವಹನ ಇತಿಹಾಸ",
    clear: "ತೆರವುಗೊಳಿಸಿ",
    sosTitle: "ತುರ್ತು SOS",
    sosSubtitle: "ಆಫ್‌ಲೈನ್ ಮೆಶ್‌ನಲ್ಲಿ ಹೆಚ್ಚಿನ ಆದ್ಯತೆಯ ವಿಪತ್ತು ಸಂಕೇತವನ್ನು ಪ್ರಸಾರ ಮಾಡಿ",
    alertCyclone: "ಚಂಡಮಾರುತ ಎಚ್ಚರಿಕೆ (Cyclone Alert)",
    alertFlood: "ಧಿಡೀರ್ ಪ್ರವಾಹ (Flash Flood)",
    alertMedical: "ವೈದ್ಯಕೀಯ ತುರ್ತುಸ್ಥಿತಿ (Medical Emergency)",
    alertComms: "ಸಂವಹನ ಸ್ಥಗಿತ (Comms Blackout)",
    broadcastSos: "ತುರ್ತು SOS ಪ್ರಸಾರ ಮಾಡಿ",
    settingsTitle: "ಸಂಯೋಜನೆಗಳು",
    settingsSubtitle: "iTantra ಸಂರಚನೆ",
    darkMode: "ಡಾರ್ಕ್ ಮೋಡ್",
    darkAppearanceEnabled: "ಡಾರ್ಕ್ ಮೋಡ್ ಸಕ್ರಿಯಗೊಂಡಿದೆ",
    language: "ಭಾಷೆ",
    translationModel: "ಅನುವಾದ ಮಾದರಿ",
    modelReady: "ಸಿದ್ಧ (ಆನ್-ಡಿವೈಸ್)",
    realtimeDashboard: "ನೈಜ-ಸಮಯದ ಡ್ಯಾಶ್‌ಬೋರ್ಡ್",
    open: "ತೆರೆಯಿರಿ",
    dashSubtitle: "STT · ಅನುವಾದ · TTS · E2E · ದೋಷ ದರ · RAM · CPU",
    errorRate: "ದೋಷ ದರ (Error Rate)",
    errorDrops: "0 ಪ್ಯಾಕೆಟ್ ಡ್ರಾಪ್ (0.00%)",
    packetLoss: "ಪ್ಯಾಕೆಟ್ ನಷ್ಟ",
    meshHops: "ನೇರ 1-ಹಾಪ್ P2P",
    offlineAi: "ಆಫ್‌ಲೈನ್ AI",
    offlineAi1: "• ಆನ್-ಡಿವೈಸ್ ಆಂಡ್ರಾಯ್ಡ್ ಸ್ಪೀಚ್ ರೆಕಗ್ನಿಷನ್",
    offlineAi2: "• ಗೂಗಲ್ ML ಕಿಟ್ ಆನ್-ಡಿವೈಸ್ ಅನುವಾದ",
    offlineAi3: "• ಸ್ಥಳೀಯ ಆಂಡ್ರಾಯ್ಡ್ ಪಠ್ಯದಿಂದ ಮಾತು",
    offlineAiFooter: "ಎಲ್ಲಾ ಸಂವಹನಗಳು ಇಂಟರ್ನೆಟ್ ಅಥವಾ ಬಾಹ್ಯ ಸರ್ವರ್‌ಗಳಿಲ್ಲದೆ ಸ್ಥಳೀಯ ವೈ-ಫೈ ಮೂಲಕ ನೇರವಾಗಿ ಪೀರ್-ಟು-ಪೀರ್ ಚಲಿಸುತ್ತವೆ.",
    tabWalkie: "ವಾಕಿ",
    tabMesh: "ಮೆಶ್",
    tabMessages: "ಸಂದೇಶಗಳು",
    tabSos: "SOS",
    tabSettings: "ಸಂಯೋಜನೆಗಳು",
    you: "ನೀವು",
    peer: "ಪೀರ್",
    sos: "SOS"
  },
  ml: {
    name: "മലയാളം (Malayalam)",
    appTagline: "ഓഫ്‌ലൈൻ ആശയവിനിമയ ശൃംഖല",
    modeWalkie: "വാക്കി",
    modeCall: "സ്വകാര്യ കോൾ",
    modeGroups: "ഗ്രൂപ്പുകൾ",
    yourLanguage: "നിങ്ങളുടെ ഭാഷ",
    change: "മാറ്റുക",
    holdToTalk: "സംസാരിക്കാൻ അമർത്തിപ്പിടിക്കുക",
    listeningActive: "കേൾക്കുന്നു…",
    recognizedSpeech: "തിരിച്ചറിഞ്ഞ സംസാരം",
    sampleTranscript: "ഞങ്ങൾ സുരക്ഷിതമായ സ്ഥലത്തെത്തി.",
    standby: "ഓഫ്‌ലൈൻ തയ്യാർ",
    callTitle: "സ്വകാര്യ വൈ-ഫൈ കോൾ",
    callSubtitle: "സിമ്മോ ഇന്റർനെറ്റോ ഇല്ലാതെ തത്സമയ 16kHz PCM ഓഡിയോ",
    selectedPeerLabel: "തിരഞ്ഞെടുത്ത പിയർ:",
    startCall: "സ്വകാര്യ കോൾ ആരംഭിക്കുക",
    calling: "വിളിക്കുന്നു…",
    callInProgress: "കോൾ പുരോഗമിക്കുന്നു",
    liveAudioWith: "ഉള്ള തത്സമയ ഓഡിയോ സ്ട്രീം:",
    endCall: "കോൾ അവസാനിപ്പിക്കുക",
    groupsTitle: "ഗ്രൂപ്പുകൾ",
    groupsSubtitle: "ടീമുകൾ · സുരക്ഷ · പ്രതികരണ സംഘങ്ങൾ",
    newGroupName: "പുതിയ ഗ്രൂപ്പ് പേര്",
    createGroup: "+ സൃഷ്ടിക്കുക",
    sendGroupAlert: "ഗ്രൂപ്പ് അലേർട്ട് അയയ്ക്കുക",
    meshLink: "മെഷ് ലിങ്ക്",
    meshSubtitle: "നേരിട്ടുള്ള പിയർ-ടു-പിയർ നെറ്റ്‌വർക്ക്",
    scan: "സ്കാൻ",
    meshConnected: "കണക്റ്റുചെയ്‌തു",
    devicesDiscovered: "3 ഉപകരണങ്ങൾ കണ്ടെത്തി",
    discoveredPeers: "കണ്ടെത്തിയ പിയറുകൾ",
    messagesTitle: "സന്ദേശങ്ങൾ",
    messagesSubtitle: "ആശയവിനിമയ ചരിത്രം",
    clear: "മായ്ക്കുക",
    sosTitle: "അടിയന്തര SOS",
    sosSubtitle: "ഓഫ്‌ലൈൻ മെഷിൽ ഉയർന്ന മുൻഗണനയുള്ള അപകട സിഗ്നൽ പ്രക്ഷേപണം ചെയ്യുക",
    alertCyclone: "ചുഴലിക്കാറ്റ് മുന്നറിയിപ്പ് (Cyclone Alert)",
    alertFlood: "പെട്ടെന്നുള്ള വെള്ളപ്പൊക്കം (Flash Flood)",
    alertMedical: "വൈദ്യസഹായം (Medical Emergency)",
    alertComms: "ആശയവിനിമയ തടസ്സം (Comms Blackout)",
    broadcastSos: "അടിയന്തര SOS പ്രക്ഷേപണം ചെയ്യുക",
    settingsTitle: "ക്രമീകരണങ്ങൾ",
    settingsSubtitle: "iTantra കോൺഫിഗറേഷൻ",
    darkMode: "ഡാർക്ക് മോഡ്",
    darkAppearanceEnabled: "ഡാർക്ക് മോഡ് പ്രവർത്തനക്ഷമമാക്കി",
    language: "ഭാഷ",
    translationModel: "വിവർത്തന മോഡൽ",
    modelReady: "തയ്യാർ (ഓൺ-ഡിവൈസ്)",
    realtimeDashboard: "തത്സമയ ഡാഷ്‌ബോർഡ്",
    open: "തുറക്കുക",
    dashSubtitle: "STT · വിവർത്തനം · TTS · E2E · പിശക് നിരക്ക് · RAM · CPU",
    errorRate: "പിശക് നിരക്ക് (Error Rate)",
    errorDrops: "0 പാക്കറ്റ് ഡ്രോപ്പുകൾ (0.00%)",
    packetLoss: "പാക്കറ്റ് നഷ്ടം",
    meshHops: "ഡയറക്ട് 1-ഹോപ്പ് P2P",
    offlineAi: "ഓഫ്‌ലൈൻ AI",
    offlineAi1: "• ഓൺ-ഡിവൈസ് ആൻഡ്രോയിഡ് സ്പീച്ച് റെക്കഗ്നിഷൻ",
    offlineAi2: "• ഗൂഗിൾ ML കിറ്റ് ഓൺ-ഡിവൈസ് വിവർത്തനം",
    offlineAi3: "• നേറ്റീവ് ആൻഡ്രോയിഡ് ടെക്‌സ്റ്റ്-ടു-സ്പീച്ച്",
    offlineAiFooter: "എല്ലാ പ്രധാന ആശയവിനിമയങ്ങളും ഇന്റർനെറ്റോ ബാഹ്യ സെർവറുകളോ ഇല്ലാതെ പ്രാദേശിക വൈ-ഫൈ വഴി നേരിട്ട് പിയർ-ടു-പിയർ ആയി പ്രവർത്തിക്കുന്നു.",
    tabWalkie: "വാക്കി",
    tabMesh: "മെഷ്",
    tabMessages: "സന്ദേശങ്ങൾ",
    tabSos: "SOS",
    tabSettings: "ക്രമീകരണങ്ങൾ",
    you: "നിങ്ങൾ",
    peer: "പിയർ",
    sos: "SOS"
  },
  pa: {
    name: "ਪੰਜਾਬੀ (Punjabi)",
    appTagline: "ਆਫਲਾਈਨ ਸੰਚਾਰ ਨੈੱਟਵਰਕ",
    modeWalkie: "ਵਾਕੀ",
    modeCall: "ਨਿੱਜੀ ਕਾਲ",
    modeGroups: "ਗਰੁੱਪ",
    yourLanguage: "ਤੁਹਾਡੀ ਭਾਸ਼ਾ",
    change: "ਬਦਲੋ",
    holdToTalk: "ਬੋਲਣ ਲਈ ਦਬਾ ਕੇ ਰੱਖੋ",
    listeningActive: "ਸੁਣ ਰਿਹਾ ਹੈ…",
    recognizedSpeech: "ਪਛਾਣੀ ਗਈ ਆਵਾਜ਼",
    sampleTranscript: "ਅਸੀਂ ਸੁਰੱਖਿਅਤ ਸਥਾਨ ਤੇ ਪਹੁੰਚ ਗਏ ਹਾਂ।",
    standby: "ਆਫਲਾਈਨ ਤਿਆਰ",
    callTitle: "ਨਿੱਜੀ ਵਾਈ-ਫਾਈ ਕਾਲ",
    callSubtitle: "ਸਿਮ ਜਾਂ ਇੰਟਰਨੈਟ ਤੋਂ ਬਿਨਾਂ ਰੀਅਲ-ਟਾਈਮ 16kHz PCM ਆਡੀਓ",
    selectedPeerLabel: "ਚੁਣਿਆ ਗਿਆ ਪੀਅਰ:",
    startCall: "ਨਿੱਜੀ ਕਾਲ ਸ਼ੁਰੂ ਕਰੋ",
    calling: "ਕਾਲ ਕਰ ਰਿਹਾ ਹੈ…",
    callInProgress: "ਕਾਲ ਜਾਰੀ ਹੈ",
    liveAudioWith: "ਨਾਲ ਲਾਈਵ ਆਡੀਓ ਸਟ੍ਰੀਮ:",
    endCall: "ਕਾਲ ਖਤਮ ਕਰੋ",
    groupsTitle: "ਗਰੁੱਪ",
    groupsSubtitle: "ਟੀਮਾਂ · ਸੁਰੱਖਿਆ · ਪ੍ਰਤੀਕਿਰਿਆ ਯੂਨਿਟਾਂ",
    newGroupName: "ਨਵਾਂ ਗਰੁੱਪ ਨਾਮ",
    createGroup: "+ ਬਣਾਓ",
    sendGroupAlert: "ਗਰੁੱਪ ਅਲਰਟ ਭੇਜੋ",
    meshLink: "ਮੈਸ਼ ਲਿੰਕ",
    meshSubtitle: "ਸਿੱਧਾ ਪੀਅਰ-ਟੂ-ਪੀਅਰ ਨੈੱਟਵਰਕ",
    scan: "ਸਕੈਨ",
    meshConnected: "ਕਨੈਕਟ ਕੀਤਾ",
    devicesDiscovered: "3 ਉਪਕਰਣ ਲੱਭੇ ਗਏ",
    discoveredPeers: "ਲੱਭੇ ਗਏ ਪੀਅਰ",
    messagesTitle: "ਸੁਨੇਹੇ",
    messagesSubtitle: "ਸੰਚਾਰ ਇਤਿਹਾਸ",
    clear: "ਸਾਫ਼ ਕਰੋ",
    sosTitle: "ਐਮਰਜੈਂਸੀ SOS",
    sosSubtitle: "ਆਫਲਾਈਨ ਮੈਸ਼ 'ਤੇ ਉੱਚ-ਤਰਜੀਹੀ ਸੰਕਟ ਸਿਗਨਲ ਪ੍ਰਸਾਰਿਤ ਕਰੋ",
    alertCyclone: "ਚੱਕਰਵਾਤ ਚਿਤਾਵਨੀ (Cyclone Alert)",
    alertFlood: "ਅਚਾਨਕ ਹੜ੍ਹ (Flash Flood)",
    alertMedical: "ਡਾਕਟਰੀ ਐਮਰਜੈਂਸੀ (Medical Emergency)",
    alertComms: "ਸੰਚਾਰ ਬਲੈਕਆਉਟ (Comms Blackout)",
    broadcastSos: "ਐਮਰਜੈਂਸੀ SOS ਪ੍ਰਸਾਰਿਤ ਕਰੋ",
    settingsTitle: "ਸੈਟਿੰਗਾਂ",
    settingsSubtitle: "iTantra ਕੌਂਫਿਗਰੇਸ਼ਨ",
    darkMode: "ਡਾਰਕ ਮੋਡ",
    darkAppearanceEnabled: "ਡਾਰਕ ਮੋਡ ਚਾਲੂ ਹੈ",
    language: "ਭਾਸ਼ਾ",
    translationModel: "ਅਨੁਵਾਦ ਮਾਡਲ",
    modelReady: "ਤਿਆਰ (ਆਨ-ਡਿਵਾਈਸ)",
    realtimeDashboard: "ਰੀਅਲ-ਟਾਈਮ ਡੈਸ਼ਬੋਰਡ",
    open: "ਖੋਲ੍ਹੋ",
    dashSubtitle: "STT · ਅਨੁਵਾਦ · TTS · E2E · ਗਲਤੀ ਦਰ · RAM · CPU",
    errorRate: "ਗਲਤੀ ਦਰ (Error Rate)",
    errorDrops: "0 ਪੈਕੇਟ ਡਰਾਪ (0.00%)",
    packetLoss: "ਪੈਕੇਟ ਨੁਕਸਾਨ",
    meshHops: "ਸਿੱਧਾ 1-ਹੌਪ P2P",
    offlineAi: "ਆਫਲਾਈਨ AI",
    offlineAi1: "• ਆਨ-ਡਿਵਾਈਸ ਐਂਡਰਾਇਡ ਸਪੀਚ ਪਛਾਣ",
    offlineAi2: "• ਗੂਗਲ ML ਕਿੱਟ ਆਨ-ਡਿਵਾਈਸ ਅਨੁਵਾਦ",
    offlineAi3: "• ਮੂਲ ਐਂਡਰਾਇਡ ਟੈਕਸਟ-ਟੂ-ਸਪੀਚ",
    offlineAiFooter: "ਸਾਰਾ ਮੁੱਖ ਸੰਚਾਰ ਇੰਟਰਨੈਟ ਜਾਂ ਬਾਹਰੀ ਸਰਵਰਾਂ ਤੋਂ ਬਿਨਾਂ ਸਥਾਨਕ ਵਾਈ-ਫਾਈ 'ਤੇ ਸਿੱਧਾ ਪੀਅਰ-ਟੂ-ਪੀਅਰ ਚੱਲਦਾ ਹੈ।",
    tabWalkie: "ਵਾਕੀ",
    tabMesh: "ਮੈਸ਼",
    tabMessages: "ਸੁਨੇਹੇ",
    tabSos: "SOS",
    tabSettings: "ਸੈਟਿੰਗਾਂ",
    you: "ਤੁਸੀਂ",
    peer: "ਪੀਅਰ",
    sos: "SOS"
  },
  en: {
    name: "English",
    appTagline: "Offline communication network",
    modeWalkie: "Walkie",
    modeCall: "Private Call",
    modeGroups: "Groups",
    yourLanguage: "Your language",
    change: "CHANGE",
    holdToTalk: "HOLD TO TALK",
    listeningActive: "LISTENING…",
    recognizedSpeech: "Recognized speech",
    sampleTranscript: "We have reached safe location successfully.",
    standby: "Offline ready",
    callTitle: "Private Wi-Fi Call",
    callSubtitle: "Real-time bidirectional 16kHz PCM audio without SIM or internet",
    selectedPeerLabel: "Selected Peer:",
    startCall: "START PRIVATE CALL",
    calling: "Calling…",
    callInProgress: "CALL IN PROGRESS",
    liveAudioWith: "Live audio stream with:",
    endCall: "END CALL",
    groupsTitle: "Groups",
    groupsSubtitle: "Teams · Security · Response units",
    newGroupName: "New group name",
    createGroup: "+ CREATE",
    sendGroupAlert: "SEND GROUP ALERT",
    meshLink: "Mesh Link",
    meshSubtitle: "Direct peer-to-peer network",
    scan: "SCAN",
    meshConnected: "Connected",
    devicesDiscovered: "3 device(s) discovered",
    discoveredPeers: "Discovered Peers",
    messagesTitle: "Messages",
    messagesSubtitle: "Communication history",
    clear: "CLEAR",
    sosTitle: "Emergency SOS",
    sosSubtitle: "Broadcast high-priority distress signal over offline mesh",
    alertCyclone: "Cyclone Alert",
    alertFlood: "Flash Flood",
    alertMedical: "Medical Emergency",
    alertComms: "Comms Blackout",
    broadcastSos: "BROADCAST EMERGENCY SOS",
    settingsTitle: "Settings",
    settingsSubtitle: "iTantra configuration",
    darkMode: "Dark mode",
    darkAppearanceEnabled: "Dark appearance enabled",
    language: "Language",
    translationModel: "Translation Model",
    modelReady: "READY (On-Device)",
    realtimeDashboard: "Real-time Dashboard",
    open: "OPEN",
    dashSubtitle: "STT · Translation · TTS · E2E · Error Rate · RAM · CPU",
    errorRate: "Error Rate",
    errorDrops: "0 packet drops (Zero loss)",
    packetLoss: "Packet Loss",
    meshHops: "Direct 1-Hop P2P",
    offlineAi: "Offline AI Architecture",
    offlineAi1: "• On-device Android speech recognition",
    offlineAi2: "• Google ML Kit on-device translation",
    offlineAi3: "• Native Android Text-to-Speech",
    offlineAiFooter: "All core communication runs strictly peer-to-peer over local Wi-Fi without internet or external servers.",
    tabWalkie: "Walkie",
    tabMesh: "Mesh",
    tabMessages: "Messages",
    tabSos: "SOS",
    tabSettings: "Settings",
    you: "You",
    peer: "Peer",
    sos: "SOS"
  }
};

// State
let currentLang = 'mr';
let currentTab = 0;
let currentWalkieMode = 0;
let soundEnabled = true;
let isRecording = false;
let callActive = false;
let callSeconds = 0;
let callInterval = null;

// Audio Context for Web Synthesized Sound Effects
let audioCtx = null;
function getAudioContext() {
  if (!audioCtx) {
    audioCtx = new (window.AudioContext || window.webkitAudioContext)();
  }
  return audioCtx;
}

// Play UI Click Beep
function playBeep(freq = 600, duration = 0.08) {
  if (!soundEnabled) return;
  try {
    const ctx = getAudioContext();
    const osc = ctx.createOscillator();
    const gain = ctx.createGain();
    osc.frequency.value = freq;
    osc.type = 'sine';
    gain.gain.setValueAtTime(0.15, ctx.currentTime);
    gain.gain.exponentialRampToValueAtTime(0.01, ctx.currentTime + duration);
    osc.connect(gain);
    gain.connect(ctx.destination);
    osc.start();
    osc.stop(ctx.currentTime + duration);
  } catch (e) {
    console.error(e);
  }
}

// Play Emergency Siren Tone
function playEmergencySiren() {
  if (!soundEnabled) return;
  try {
    const ctx = getAudioContext();
    const now = ctx.currentTime;

    const osc = ctx.createOscillator();
    const gain = ctx.createGain();
    osc.type = 'sawtooth';

    // Two-tone European / Disaster siren pitch alternation
    osc.frequency.setValueAtTime(960, now);
    osc.frequency.setValueAtTime(800, now + 0.3);
    osc.frequency.setValueAtTime(960, now + 0.6);
    osc.frequency.setValueAtTime(800, now + 0.9);
    osc.frequency.setValueAtTime(960, now + 1.2);
    osc.frequency.setValueAtTime(800, now + 1.5);

    gain.gain.setValueAtTime(0.25, now);
    gain.gain.exponentialRampToValueAtTime(0.01, now + 2.0);

    osc.connect(gain);
    gain.connect(ctx.destination);
    osc.start(now);
    osc.stop(now + 2.0);
  } catch (e) {
    console.error(e);
  }
}

// --- TAB SWITCHING ---
function switchTab(index) {
  playBeep(520, 0.05);
  currentTab = index;

  // Update nav buttons
  for (let i = 0; i < 5; i++) {
    const tabBtn = document.getElementById(`tab-${i}`);
    if (tabBtn) {
      if (i === index) tabBtn.classList.add('active');
      else tabBtn.classList.remove('active');
    }
  }

  // Update screens
  const screens = ['view-walkie', 'view-mesh', 'view-messages', 'view-sos', 'view-settings'];
  screens.forEach((id, i) => {
    const el = document.getElementById(id);
    if (el) {
      if (i === index) el.classList.add('active');
      else el.classList.remove('active');
    }
  });

  // Close analytics subview if open
  closeAnalyticsScreen();
}

// --- WALKIE MODES SWITCHING ---
function switchWalkieMode(mode) {
  playBeep(640, 0.05);
  currentWalkieMode = mode;

  const modeBtns = ['btn-mode-walkie', 'btn-mode-call', 'btn-mode-groups'];
  const modePanels = ['panel-mode-walkie', 'panel-mode-call', 'panel-mode-groups'];

  modeBtns.forEach((id, i) => {
    const btn = document.getElementById(id);
    if (btn) {
      if (i === mode) btn.classList.add('active');
      else btn.classList.remove('active');
    }
  });

  modePanels.forEach((id, i) => {
    const panel = document.getElementById(id);
    if (panel) {
      if (i === mode) panel.classList.add('active');
      else panel.classList.remove('active');
    }
  });
}

// --- DYNAMIC LANGUAGE SWITCHING ---
function setLanguage(langCode) {
  if (!I18N[langCode]) return;
  currentLang = langCode;
  const s = I18N[langCode];

  // Update active modal button
  document.querySelectorAll('.lang-opt').forEach(btn => {
    btn.classList.toggle('active', btn.getAttribute('onclick').includes(langCode));
  });

  // Update UI Elements
  document.getElementById('currentLanguageDisplay').innerText = s.name;
  document.getElementById('settingsCurrentLang').innerText = s.name;
  document.getElementById('dashLangVal').innerText = s.name;

  document.getElementById('txt-appTagline').innerText = s.appTagline;
  document.getElementById('txt-modeWalkie').innerText = s.modeWalkie;
  document.getElementById('txt-modeCall').innerText = s.modeCall;
  document.getElementById('txt-modeGroups').innerText = s.modeGroups;
  document.getElementById('txt-yourLanguage').innerText = s.yourLanguage;
  document.getElementById('txt-changeLangBtn').innerText = s.change;

  // Walkie Panel
  document.getElementById('walkieStatus').innerText = s.standby;
  document.getElementById('pttLabel').innerText = s.holdToTalk;
  document.getElementById('txt-recognizedSpeech').innerText = s.recognizedSpeech;
  document.getElementById('transcriptText').innerText = `"${s.sampleTranscript}"`;

  // Call Panel
  document.getElementById('txt-callTitle').innerText = s.callTitle;
  document.getElementById('txt-callSubtitle').innerText = s.callSubtitle;
  document.getElementById('txt-selectedPeerLabel').innerHTML = `${s.selectedPeerLabel} <span class="text-accent">Rescue Unit A</span>`;
  document.getElementById('txt-startCall').innerText = s.startCall;
  document.getElementById('txt-callInProgress').innerText = s.callInProgress;
  document.getElementById('txt-liveAudioWith').innerText = `${s.liveAudioWith} Rescue Unit A`;
  document.getElementById('txt-endCall').innerText = s.endCall;

  // Groups
  document.getElementById('txt-groupsTitle').innerText = s.groupsTitle;
  document.getElementById('txt-groupsSubtitle').innerText = s.groupsSubtitle;
  document.getElementById('newGroupNameInput').placeholder = s.newGroupName;
  document.getElementById('txt-createGroup').innerText = s.createGroup;
  if (document.getElementById('txt-groupWalkieHold')) {
    document.getElementById('txt-groupWalkieHold').innerText = s.groupWalkieHold || 'HOLD TO TALK TO GROUP';
  }
  if (document.getElementById('txt-groupSosBtn')) {
    document.getElementById('txt-groupSosBtn').innerText = s.groupSosBtn || 'GROUP EMERGENCY SOS';
  }
  document.getElementById('txt-sendGroupAlert').innerHTML = `<i class="fa-solid fa-paper-plane"></i> <span>${s.sendGroupAlert}</span>`;

  // Emergency Modal
  if (document.getElementById('emergencyModalTitle')) {
    document.getElementById('emergencyModalTitle').innerText = s.emergencySosAlertHeader || 'CRITICAL EMERGENCY ALERT';
  }
  if (document.getElementById('emergencyModalPrompt')) {
    document.getElementById('emergencyModalPrompt').innerText = s.vibratingAlertPrompt || 'Siren and vibration looping continuously until acknowledged';
  }
  if (document.getElementById('txt-ackAlert')) {
    document.getElementById('txt-ackAlert').innerText = s.ackAlert || 'ACKNOWLEDGE & STOP ALARM';
  }

  // Mesh
  document.getElementById('txt-meshLink').innerText = s.meshLink;
  document.getElementById('txt-meshSubtitle').innerText = s.meshSubtitle;
  document.getElementById('txt-scan').innerText = s.scan;
  document.getElementById('txt-meshConnected').innerText = s.meshConnected;
  document.getElementById('txt-devicesDiscovered').innerText = s.devicesDiscovered;
  document.getElementById('txt-discoveredPeers').innerText = s.discoveredPeers;

  // Messages
  document.getElementById('txt-messagesTitle').innerText = s.messagesTitle;
  document.getElementById('txt-messagesSubtitle').innerText = s.messagesSubtitle;
  document.getElementById('txt-clear').innerText = s.clear;

  // SOS
  document.getElementById('txt-sosTitle').innerText = s.sosTitle;
  document.getElementById('txt-sosSubtitle').innerText = s.sosSubtitle;
  document.getElementById('txt-alertCyclone').innerText = s.alertCyclone;
  document.getElementById('txt-alertFlood').innerText = s.alertFlood;
  document.getElementById('txt-alertMedical').innerText = s.alertMedical;
  document.getElementById('txt-alertComms').innerText = s.alertComms;
  document.getElementById('txt-broadcastSos').innerHTML = `<i class="fa-solid fa-triangle-exclamation"></i> <span>${s.broadcastSos}</span>`;

  // Settings
  document.getElementById('txt-settingsTitle').innerText = s.settingsTitle;
  document.getElementById('txt-settingsSubtitle').innerText = s.settingsSubtitle;
  document.getElementById('txt-darkMode').innerText = s.darkMode;
  document.getElementById('txt-darkAppearanceEnabled').innerText = s.darkAppearanceEnabled;
  document.getElementById('txt-language').innerText = s.language;
  document.getElementById('txt-settingsChange').innerText = s.change;
  document.getElementById('txt-translationModel').innerText = s.translationModel;
  document.getElementById('txt-modelReady').innerText = s.modelReady;
  document.getElementById('txt-realtimeDashboard').innerText = s.realtimeDashboard;
  document.getElementById('txt-open').innerText = s.open;
  if (document.getElementById('txt-dashSubtitle')) {
    document.getElementById('txt-dashSubtitle').innerText = s.dashSubtitle;
  }
  if (document.getElementById('txt-dashErrorRate')) {
    document.getElementById('txt-dashErrorRate').innerText = s.errorRate;
  }
  if (document.getElementById('txt-dashErrorDrops')) {
    document.getElementById('txt-dashErrorDrops').innerText = s.errorDrops;
  }
  if (document.getElementById('txt-dashPacketLoss')) {
    document.getElementById('txt-dashPacketLoss').innerText = s.packetLoss;
  }
  if (document.getElementById('txt-dashMeshHops')) {
    document.getElementById('txt-dashMeshHops').innerText = s.meshHops;
  }
  document.getElementById('txt-offlineAi').innerText = s.offlineAi;
  document.getElementById('txt-offlineAi1').innerText = s.offlineAi1;
  document.getElementById('txt-offlineAi2').innerText = s.offlineAi2;
  document.getElementById('txt-offlineAi3').innerText = s.offlineAi3;
  document.getElementById('txt-offlineAiFooter').innerText = s.offlineAiFooter;

  // Nav Bar
  document.getElementById('txt-navWalkie').innerText = s.tabWalkie;
  document.getElementById('txt-navMesh').innerText = s.tabMesh;
  document.getElementById('txt-navMessages').innerText = s.tabMessages;
  document.getElementById('txt-navSos').innerText = s.tabSos;
  document.getElementById('txt-navSettings').innerText = s.tabSettings;

  // Close modal
  closeLanguageDialog();
  playBeep(720, 0.08);
}

// Modal open/close
function openLanguageDialog() {
  document.getElementById('languageModal').classList.add('active');
}
function closeLanguageDialog() {
  document.getElementById('languageModal').classList.remove('active');
}

// --- PUSH-TO-TALK WALKIE SIMULATION ---
function startWalkieTalk(e) {
  if (e) e.preventDefault();
  if (isRecording) return;
  isRecording = true;

  playBeep(880, 0.08);
  const ptt = document.getElementById('pttButton');
  const wave = document.getElementById('waveform');
  const label = document.getElementById('pttLabel');
  const status = document.getElementById('walkieStatus');
  const s = I18N[currentLang];

  ptt.classList.add('recording');
  wave.classList.add('active-pulse');
  label.innerText = s.listeningActive;
  status.innerText = s.listeningActive;
}

function stopWalkieTalk(e) {
  if (e) e.preventDefault();
  if (!isRecording) return;
  isRecording = false;

  playBeep(440, 0.06);
  const ptt = document.getElementById('pttButton');
  const wave = document.getElementById('waveform');
  const label = document.getElementById('pttLabel');
  const status = document.getElementById('walkieStatus');
  const s = I18N[currentLang];

  ptt.classList.remove('recording');
  wave.classList.remove('active-pulse');
  label.innerText = s.holdToTalk;
  status.innerText = s.standby;

  // Add message to stream
  appendMessage(s.you, s.sampleTranscript, 'you');

  // Simulated browser Text-To-Speech if speech synthesis is available
  if ('speechSynthesis' in window && soundEnabled) {
    try {
      const utter = new SpeechSynthesisUtterance(s.sampleTranscript);
      window.speechSynthesis.speak(utter);
    } catch (err) {
      console.warn(err);
    }
  }
}

// --- PRIVATE CALL SIMULATION ---
function simulateStartCall() {
  playBeep(700, 0.1);
  callActive = true;
  document.getElementById('callActionArea').style.display = 'none';
  const modal = document.getElementById('activeCallModal');
  modal.classList.add('active');

  callSeconds = 0;
  document.getElementById('callDuration').innerText = '00:00';
  if (callInterval) clearInterval(callInterval);

  callInterval = setInterval(() => {
    callSeconds++;
    const m = String(Math.floor(callSeconds / 60)).padStart(2, '0');
    const sec = String(callSeconds % 60).padStart(2, '0');
    document.getElementById('callDuration').innerText = `${m}:${sec}`;
  }, 1000);
}

function simulateEndCall() {
  playBeep(350, 0.12);
  callActive = false;
  if (callInterval) clearInterval(callInterval);
  document.getElementById('activeCallModal').classList.remove('active');
  document.getElementById('callActionArea').style.display = 'block';
}

// --- GROUPS SIMULATION ---
function createGroup() {
  const input = document.getElementById('newGroupNameInput');
  const val = input.value.trim();
  if (!val) return;
  playBeep(600, 0.05);

  const container = document.getElementById('groupListContainer');
  const item = document.createElement('div');
  item.className = 'group-item';
  item.innerHTML = `
    <span class="group-name">${val}</span>
    <button class="btn-tiny" onclick="selectGroupItem(this)">SELECT</button>
  `;
  container.prepend(item);
  input.value = '';
}

function selectGroupItem(btn) {
  playBeep(600, 0.05);
  document.querySelectorAll('.group-item').forEach(i => {
    i.classList.remove('selected');
    const b = i.querySelector('button');
    if (b) { b.innerText = 'SELECT'; b.className = 'btn-tiny'; }
  });
  const parent = btn.closest('.group-item');
  parent.classList.add('selected');
  btn.innerText = 'SELECTED';
  btn.className = 'btn-tiny btn-accent';
}

function sendGroupAlert() {
  const input = document.getElementById('groupMsgInput');
  const val = input.value.trim() || 'Urgent group dispatch alert!';
  playBeep(750, 0.08);

  const selectedGroup = document.querySelector('.group-item.selected .group-name')?.innerText || 'Disaster Response Alpha';
  appendMessage(selectedGroup, val, 'peer');
  input.value = '';
  switchTab(2); // Go to messages
}

// --- GROUP WALKIE TALKIE & GROUP SOS ---
let isGroupRecording = false;
let sirenInterval = null;

function startGroupWalkieTalk(e) {
  if (e) e.preventDefault();
  if (isGroupRecording) return;
  isGroupRecording = true;
  playBeep(880, 0.08);

  const btn = document.getElementById('groupPttBtn');
  if (btn) btn.classList.add('recording');
  const lbl = document.getElementById('txt-groupWalkieHold');
  if (lbl) lbl.innerText = I18N[currentLang]?.listeningActive || 'LISTENING...';
}

function stopGroupWalkieTalk(e) {
  if (e) e.preventDefault();
  if (!isGroupRecording) return;
  isGroupRecording = false;
  playBeep(440, 0.06);

  const btn = document.getElementById('groupPttBtn');
  if (btn) btn.classList.remove('recording');
  const lbl = document.getElementById('txt-groupWalkieHold');
  if (lbl) lbl.innerText = I18N[currentLang]?.groupWalkieHold || 'HOLD TO TALK TO GROUP';

  const selectedGroup = document.querySelector('.group-item.selected .group-name')?.innerText || 'Disaster Response Alpha';
  const sampleMsg = I18N[currentLang]?.sampleTranscript || 'आम्ही सुरक्षित ठिकाणी पोहोचलो आहोत.';
  appendMessage(selectedGroup, sampleMsg, 'you');

  if ('speechSynthesis' in window && soundEnabled) {
    try {
      const utter = new SpeechSynthesisUtterance(sampleMsg);
      window.speechSynthesis.speak(utter);
    } catch (err) {
      console.warn(err);
    }
  }

  setTimeout(() => switchTab(2), 500);
}

function sendGroupSosAlert() {
  const selectedGroup = document.querySelector('.group-item.selected .group-name')?.innerText || 'Disaster Response Alpha';
  const alertMsg = (currentLang === 'mr')
    ? 'गट आणीबाणी इशारा: त्वरित मदत आणि बचाव पथकाची आवश्यकता आहे!'
    : (currentLang === 'hi')
    ? 'समूह आपातकालीन चेतावनी: तत्काल चिकित्सा एवं बचाव दल की आवश्यकता है!'
    : 'CRITICAL GROUP SOS: Immediate rescue and medical assistance required!';

  appendMessage(`SOS [${selectedGroup}]`, alertMsg, 'sos');
  triggerContinuousEmergencyAlert(alertMsg, "Field Unit", selectedGroup);
}

function triggerContinuousEmergencyAlert(message, sender, group) {
  const modal = document.getElementById('emergencySosModal');
  const title = document.getElementById('emergencyModalTitle');
  const body = document.getElementById('emergencyModalBody');
  const prompt = document.getElementById('emergencyModalPrompt');
  const s = I18N[currentLang] || {};

  if (title) title.innerText = s.emergencySosAlertHeader || 'CRITICAL EMERGENCY ALERT';
  if (body) body.innerText = `"${message}"`;
  if (prompt) prompt.innerText = s.vibratingAlertPrompt || 'Siren and vibration looping continuously until acknowledged';

  if (modal) modal.classList.add('active');
  const phone = document.getElementById('phoneSimulatorFrame');
  if (phone) phone.classList.add('vibrating');

  // Loop siren sound and vibration until user acknowledges
  playEmergencySiren();
  if ('vibrate' in navigator) {
    navigator.vibrate([200, 100, 200, 100, 200, 300, 500, 200, 500, 200, 500, 300, 200, 100, 200, 100, 200]);
  }

  if (sirenInterval) clearInterval(sirenInterval);
  sirenInterval = setInterval(() => {
    playEmergencySiren();
    if ('vibrate' in navigator) {
      navigator.vibrate([200, 100, 200, 100, 200, 300, 500, 200, 500, 200, 500, 300, 200, 100, 200, 100, 200]);
    }
  }, 2300);

  // Spoken voice alert in receiver's translated language
  if ('speechSynthesis' in window && soundEnabled) {
    try {
      window.speechSynthesis.cancel();
      const utter = new SpeechSynthesisUtterance(message);
      window.speechSynthesis.speak(utter);
    } catch (e) {
      console.warn(e);
    }
  }
}

function acknowledgeEmergencyAlert() {
  if (sirenInterval) {
    clearInterval(sirenInterval);
    sirenInterval = null;
  }
  const modal = document.getElementById('emergencySosModal');
  if (modal) modal.classList.remove('active');
  const phone = document.getElementById('phoneSimulatorFrame');
  if (phone) phone.classList.remove('vibrating');

  if ('speechSynthesis' in window) {
    window.speechSynthesis.cancel();
  }
  playBeep(440, 0.12);
}

// --- MESH SCAN SIMULATION ---
function triggerMeshScan() {
  const btn = document.getElementById('btn-scan');
  btn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> <span>SCANNING…</span>';
  playBeep(800, 0.08);

  setTimeout(() => {
    btn.innerHTML = `<i class="fa-solid fa-arrows-rotate"></i> <span>${I18N[currentLang].scan}</span>`;
    document.getElementById('txt-devicesDiscovered').innerText = '4 device(s) discovered';
  }, 1200);
}

function selectPeer(btn, name) {
  playBeep(600, 0.05);
  document.querySelectorAll('.peer-card').forEach(c => {
    c.classList.remove('selected');
    const b = c.querySelector('button');
    if (b) { b.innerText = 'CONNECT'; b.className = 'btn-tiny'; }
  });
  const parent = btn.closest('.peer-card');
  parent.classList.add('selected');
  btn.innerText = 'SELECTED';
  btn.className = 'btn-tiny btn-accent';
  document.getElementById('callPeerName').innerText = name;
}

// --- EMERGENCY SOS SIMULATION ---
function triggerSosPreset(btn) {
  const custom = document.getElementById('customSosText');
  custom.value = btn.innerText;
  playBeep(700, 0.05);
}

function broadcastEmergencySos() {
  const custom = document.getElementById('customSosText');
  const msg = custom.value.trim() || I18N[currentLang].alertCyclone;

  // Screen Flash effect
  const flash = document.getElementById('sosFlash');
  if (flash) {
    flash.classList.add('active');
    setTimeout(() => flash.classList.remove('active'), 1800);
  }

  // Add SOS message
  appendMessage('SOS', msg, 'sos');
  custom.value = '';

  // Trigger continuous looping siren & vibration with Acknowledge Button
  triggerContinuousEmergencyAlert(msg, 'Emergency Command', 'Mesh Broadcast');
}

// --- MESSAGES STREAM ---
function appendMessage(tag, content, type = 'you') {
  const stream = document.getElementById('messagesStream');
  const bubble = document.createElement('div');
  bubble.className = `msg-bubble msg-${type}`;

  if (type === 'sos') {
    bubble.innerHTML = `
      <i class="fa-solid fa-triangle-exclamation"></i>
      <span class="msg-tag">SOS:</span>
      <span class="msg-content">${content}</span>
    `;
  } else {
    bubble.innerHTML = `
      <span class="msg-tag">${tag}:</span>
      <span class="msg-content">${content}</span>
    `;
  }
  stream.appendChild(bubble);
  stream.scrollTop = stream.scrollHeight;
}

function clearMessages() {
  playBeep(400, 0.05);
  document.getElementById('messagesStream').innerHTML = '';
}

// --- ANALYTICS DASHBOARD ---
function openAnalyticsScreen() {
  playBeep(650, 0.05);
  document.getElementById('subview-analytics').classList.add('active');
}

function closeAnalyticsScreen() {
  const el = document.getElementById('subview-analytics');
  if (el) el.classList.remove('active');
}

// --- THEME TOGGLE INSIDE SIMULATOR ---
function toggleSimTheme(checkbox) {
  const phone = document.getElementById('phoneScreen');
  if (checkbox.checked) {
    phone.style.setProperty('--bg-phone', '#0B0F14');
    phone.style.setProperty('--phone-card', '#151B22');
  } else {
    phone.style.setProperty('--bg-phone', '#F5F7FA');
    phone.style.setProperty('--phone-card', '#FFFFFF');
  }
}

// --- GLOBAL SOUND TOGGLE ---
document.getElementById('soundToggleBtn').addEventListener('click', function() {
  soundEnabled = !soundEnabled;
  this.innerHTML = soundEnabled ? '<i class="fa-solid fa-volume-high"></i>' : '<i class="fa-solid fa-volume-xmark"></i>';
  if (soundEnabled) playBeep(800, 0.05);
});

// --- CLOCK IN STATUS BAR ---
function updateClock() {
  const now = new Date();
  const h = String(now.getHours()).padStart(2, '0');
  const m = String(now.getMinutes()).padStart(2, '0');
  const clock = document.getElementById('simTime');
  if (clock) clock.innerText = `${h}:${m}`;
}
setInterval(updateClock, 1000);
updateClock();

// --- PHONE SCREEN SIZE TOGGLE ---
function setPhoneSize(size) {
  const frame = document.getElementById('phoneSimulatorFrame');
  if (!frame) return;

  document.querySelectorAll('.btn-size-toggle').forEach(b => b.classList.remove('active'));
  frame.classList.remove('size-compact', 'size-large', 'size-max');

  if (size === 'compact') {
    frame.classList.add('size-compact');
    const btn = document.getElementById('btnSizeCompact');
    if (btn) btn.classList.add('active');
  } else if (size === 'max') {
    frame.classList.add('size-max');
    const btn = document.getElementById('btnSizeMax');
    if (btn) btn.classList.add('active');
  } else {
    frame.classList.add('size-large');
    const btn = document.getElementById('btnSizeLarge');
    if (btn) btn.classList.add('active');
  }

  playBeep(720, 0.04);
}

// Initial language setup
setLanguage('mr');
