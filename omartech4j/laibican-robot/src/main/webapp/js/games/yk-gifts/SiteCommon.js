/**
 * game by HE @youku.com
 * v1.1.61
 */
var gversion = "1.1.61";
var gameScreenWidth, gameScreenHeight;
var GAME_TIME = 10 * 10;
var statusGame = "none";
var bgLayer, loadingLayer, gameLayer, controlLayer;
var goodsLayer;
var adsLayer, adsMc, adsObj;
var bgAll, bgMc1, bgMc2, bgMc3, bgMc4, bgMc5;
var partsLayer, partsMovie;
var bgMc, bgWhite, bgBlack;
var topUI;
var sceneCurrent;
var goodsTotal;
var goodsCurrent;
var goodsCollectCount;
var goodsNO;
var goodsTimeArr;
var goodsMc;
var beginBtn;
var yNum1, yNum2, yNum3;
var countTimer;
var timeTxt;
var timeCount;
var soundBg;
var bolSoundLoaded = false;
var bolSoundHadStopByUser = false;
var soundIco;
var treeMc, snowMc;
var gameNameMc;
var copyrightTxt;
var tipsTxt;
var tipsNo;
var xTipsNo, yTipsNo;
var tipsMc;
var handPic;
var xHandDefault, yHandDefault;
var tipsTimer;
var introBtn, closeIntroBtn, introMc, introText;
var copyrightBtn, closeCRBtn, copyrightMc;
var overMc, resultMc, failMc1, failMc2, successMc, guaMc;
var shareWXMc, shareOtherMc;
var replayBtn1, replayBtn2, replayBtn3;
var shareBtn1, shareBtn2, shareBtn3;
var scoreFen1, scoreFen2;
var weiboBtn, qzoneBtn, tqqBtn;
var shareBackBtn1, shareBackBtn2;
var closeBtn1, closeBtn2;
var numT1, numT2, numT3, numT4;
var bolGameSuccess = false;
var bolSubmiting = false;
var bolHadSubmit = false;
var bolGameAgain = false;
var bolHadResizeEvent = false;
var dAward = 0;
var dRCode = "";
var dDID;
var dKey;
var dCode;
var xhrAjax;
var gResult = 0;
var arryKey, arryCode;
var shareData;
var shareData2;
var shareSNS;
var imglist = {};
var imgData = [{name: "bg", path: "http://r1.ykimg.com/051000005491631C6737B32C980754BF"}, {
    name: "wu_light",
    path: "http://r4.ykimg.com/051000005491633B6737B340E5079179"
}, {name: "wu_snow", path: "http://r4.ykimg.com/051000005491633B6737B30CD80D7AE1"}, {
    name: "wu_tree",
    path: "http://r4.ykimg.com/051000005491633B6737B30432036ADA"
}, {name: "wu_goods", path: "http://r4.ykimg.com/051000005491633B6737B373E90EAE25"}, {
    name: "ui_topgoods",
    path: "http://r4.ykimg.com/051000005491633B6737B34D7201BA6F"
}, {name: "ui_topbg", path: "http://r4.ykimg.com/051000005491633B6737B379BB043000"}, {
    name: "game_name",
    path: "http://r2.ykimg.com/051000005491631D6737B336E605E0A4"
}, {name: "intro_text", path: "http://r2.ykimg.com/051000005491631D6737B31C3F0CDC0E"}, {
    name: "elements",
    path: "http://r2.ykimg.com/051000005491631C6737B366A50C1EB2"
}, {name: "audio_switch", path: "http://r1.ykimg.com/0510000053EC88086737B35AEB049E52"}, {
    name: "tips_handle",
    path: "http://r3.ykimg.com/051000005491633A6737B30E220A20A2"
}, {name: "tips_select_no", path: "http://r3.ykimg.com/051000005491633A6737B33EF2086456"}, {
    name: "tips_copyright",
    path: "http://r4.ykimg.com/051000005472F29A6737B32750036A23"
}, {name: "result_bg", path: "http://r2.ykimg.com/051000005491631D6737B36FD10703E6"}, {
    name: "result_info",
    path: "images/result_info.png"
}, {name: "share_tips_weixin", path: "http://r3.ykimg.com/051000005491633A6737B31E4F0CF22D"}, {
    name: "share_buttons",
    path: "http://r3.ykimg.com/051000005491633A6737B30B61073DC9"
}];
function loadResource() {
    LGlobal.setDebug(false);
    LGlobal.backgroundColor = "#000";
    LGlobal.align = LStageAlign.TOP_MIDDLE;
    LGlobal.stageScale = LStageScaleMode.SHOW_ALL;
    LSystem.screen(LStage.FULL_SCREEN);
    windowResizeListener("add");
    bgLayer = new LSprite();
    addChild(bgLayer);
    gameLayer = new LSprite();
    addChild(gameLayer);
    controlLayer = new LSprite();
    addChild(controlLayer);
    loadingLayer = new LoadingSample5();
    addChild(loadingLayer);
    showLoadingTips();
    LLoadManage.load(imgData, function (progress) {
        loadingLayer.setProgress(progress)
    }, function (result) {
        imglist = result;
        removeChild(loadingLayer);
        loadingLayer = null;
        removeChild(tipsTxt);
        tipsTxt = null;
        LGlobal.canvasObj.width = LGlobal.canvasObj.width;
        initGame();
        initSoundBg();
        _hmt.push(["_trackPageview", "/games/christmas/load/complete"])
    });
    $(".loadingIco").css("display", "none");
    _hmt.push(["_trackPageview", "/games/christmas/load/start"])
}
function initVal() {
    goodsTotal = 16;
    sceneCurrent = 0;
    goodsCurrent = 0;
    goodsCollectCount = 0;
    goodsNO = 0;
    goodsTimeArr = [10, 10, 10, 10, 15, 15, 15, 15, 15, 15, 15, 15, 20, 20, 20, 20];
    timeCount = goodsTimeArr[goodsNO] * 10;
    dKey = "";
    dCode = "";
    arryKey = [];
    arryCode = []
}
function initGame() {
    initVal();
    bgAll = new LSprite();
    bgLayer.addChild(bgAll);
    bgMc1 = new OBJECT_ITEM("bg", null, 0, 0, 0, 0, 640, 1010);
    bgAll.addChild(bgMc1);
    var light = new OBJECT_ITEM("wu_light");
    bgMc1.addChild(light);
    treeMc = new LAnimationTimeline(new LBitmapData(imglist["wu_tree"], 0, 0, 641, 322), LGlobal.divideCoordinate(1923, 322, 1, 3));
    treeMc.x = 0;
    treeMc.y = LGlobal.height - treeMc.getHeight();
    treeMc.speed = 25;
    treeMc.stop();
    bgMc1.addChild(treeMc);
    bgMc2 = new OBJECT_ITEM("bg", null, 0, 0, 640, 0, 640, 1010);
    bgMc2.y = 1010 * 1;
    bgAll.addChild(bgMc2);
    bgMc3 = new OBJECT_ITEM("bg", null, 0, 0, 1280, 0, 640, 1010);
    bgMc3.y = 1010 * 2;
    bgAll.addChild(bgMc3);
    bgMc4 = new OBJECT_ITEM("bg", null, 0, 0, 1920, 0, 640, 1010);
    bgMc4.y = 1010 * 3;
    bgAll.addChild(bgMc4);
    bgMc5 = new OBJECT_ITEM("bg", null, 0, 0, 2560, 0, 640, 1010);
    bgMc5.y = 1010 * 4;
    bgAll.addChild(bgMc5);
    bgWhite = new LSprite();
    bgWhite.alpha = 0;
    bgWhite.visible = false;
    bgWhite.graphics.drawRect(0, "#000000", [0, 0, LGlobal.width, LGlobal.height], true, "#000000");
    controlLayer.addChild(bgWhite);
    snowMc = new OBJECT_SNOW();
    gameLayer.addChild(snowMc);
    gameNameMc = new OBJECT_ITEM("game_name");
    gameNameMc.name = "game_name";
    gameNameMc.x = (LGlobal.width - gameNameMc.getWidth()) / 2;
    gameNameMc.y = 180;
    controlLayer.addChild(gameNameMc);
    introBtn = new OBJECT_ITEM("elements", null, 0, 0, 270, 110, 260, 105);
    introBtn.name = "btn_intro";
    introBtn.x = (LGlobal.width - introBtn.getWidth()) / 2;
    introBtn.y = 580;
    introBtn.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    controlLayer.addChild(introBtn);
    beginBtn = new OBJECT_ITEM("elements", null, 0, 0, 270, 0, 260, 105);
    beginBtn.name = "btn_begin";
    beginBtn.x = (LGlobal.width - beginBtn.getWidth()) / 2;
    beginBtn.y = 450;
    beginBtn.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    controlLayer.addChild(beginBtn);
    yNum1 = gameNameMc.y;
    yNum2 = beginBtn.y;
    yNum3 = introBtn.y;
    copyrightBtn = new OBJECT_ITEM("tips_copyright", null, 0, 0, 0, 0, 51, 51);
    copyrightBtn.name = "btn_copyright";
    copyrightBtn.x = LGlobal.width - copyrightBtn.getWidth() - 20;
    copyrightBtn.y = LGlobal.height - copyrightBtn.getHeight() - 20;
    copyrightBtn.scaleX = 0.8;
    copyrightBtn.scaleY = 0.8;
    copyrightBtn.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    copyrightBtn.visible = true;
    controlLayer.addChild(copyrightBtn);
    goodsLayer = new LSprite();
    gameLayer.addChild(goodsLayer);
    copyrightTxt = new LTextField();
    copyrightTxt.text = "© 优酷";
    copyrightTxt.font = "微软雅黑";
    copyrightTxt.size = 16;
    copyrightTxt.color = "#ffffff";
    copyrightTxt.alpha = 0.7;
    bgLayer.addChild(copyrightTxt);
    copyrightTxt.x = (LGlobal.width - copyrightTxt.getWidth()) / 2;
    copyrightTxt.y = LGlobal.height - copyrightTxt.getHeight() - 20;
    topUI = new UITop();
    topUI.visible = false;
    controlLayer.addChild(topUI);
    timeTxt = new LTextField();
    timeTxt.text = timeCount / 10 + ".0 秒";
    timeTxt.font = "微软雅黑";
    timeTxt.size = 30;
    timeTxt.color = "#FFFFFF";
    timeTxt.weight = "bolder";
    timeTxt.textAlign = "center";
    topUI.addChild(timeTxt);
    timeTxt.x = 320;
    timeTxt.y = 194;
    tipsMc = new OBJECT_ITEM("tips_handle", null, 0, 0, 0, 0, 640, 1010);
    tipsMc.name = "tips_mc";
    tipsMc.visible = false;
    tipsMc.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    controlLayer.addChild(tipsMc);
    handPic = new OBJECT_ITEM("tips_handle", null, 0, 0, 640, 0, 99, 153);
    handPic.x = 120;
    handPic.y = 560;
    tipsMc.addChild(handPic);
    xHandDefault = handPic.x;
    yHandDefault = handPic.y;
    tipsNo = new OBJECT_ITEM("tips_select_no");
    tipsNo.x = (LGlobal.width - tipsNo.getWidth()) / 2;
    tipsNo.y = LGlobal.height - tipsNo.getHeight() - 5;
    tipsNo.visible = false;
    controlLayer.addChild(tipsNo);
    xTipsNo = tipsNo.x;
    yTipsNo = tipsNo.y;
    bgBlack = new LSprite();
    bgBlack.alpha = 0.7;
    bgBlack.visible = false;
    bgBlack.graphics.drawRect(0, "#000000", [0, 0, LGlobal.width, LGlobal.height], true, "#000000");
    controlLayer.addChild(bgBlack);
    introMc = new OBJECT_ITEM("result_bg", null, 0, 0, 1749, 0, 583, 836);
    introMc.x = (LGlobal.width - introMc.getWidth()) / 2;
    introMc.y = 42;
    introMc.visible = false;
    controlLayer.addChild(introMc);
    introText = new OBJECT_ITEM("intro_text");
    introText.x = 98;
    introText.y = 56;
    introMc.addChild(introText);
    var maskIntro = new LSprite();
    maskIntro.graphics.drawRect(0, "#ff0000", [70, 53, 450, 665]);
    introText.mask = maskIntro;
    closeIntroBtn = new OBJECT_ITEM("result_bg", null, 0, 0, 0, 836, 80, 80);
    closeIntroBtn.name = "btn_close_intro";
    closeIntroBtn.x = introMc.getWidth() - 60;
    closeIntroBtn.y = -15;
    closeIntroBtn.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    introMc.addChild(closeIntroBtn);
    copyrightMc = new OBJECT_ITEM("tips_copyright", null, 0, 0, 62, 0, 380, 531);
    copyrightMc.x = (LGlobal.width - copyrightMc.getWidth()) / 2;
    copyrightMc.y = (LGlobal.height - copyrightMc.getHeight()) / 2;
    copyrightMc.visible = false;
    controlLayer.addChild(copyrightMc);
    closeCRBtn = new OBJECT_ITEM("tips_copyright", null, 0, 0, 0, 60, 57, 57);
    closeCRBtn.name = "btn_copyright_close";
    closeCRBtn.x = copyrightMc.getWidth() - 35;
    closeCRBtn.y = -20;
    closeCRBtn.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    copyrightMc.addChild(closeCRBtn);
    overMc = new LSprite();
    overMc.name = "over_mc";
    overMc.visible = false;
    controlLayer.addChild(overMc);
    resultMc = new LSprite();
    resultMc.visible = false;
    overMc.addChild(resultMc);
    failMc1 = new OBJECT_ITEM("result_bg", null, 0, 0, 0, 0, 583, 836);
    failMc1.x = (LGlobal.width - failMc1.getWidth()) / 2;
    failMc1.y = 42;
    failMc1.visible = false;
    resultMc.addChild(failMc1);
    scoreFen1 = new LTextField();
    scoreFen1.text = "";
    scoreFen1.font = "微软雅黑";
    scoreFen1.size = 30;
    scoreFen1.color = "#D3392C";
    scoreFen1.weight = "bolder";
    scoreFen1.textAlign = "center";
    failMc1.addChild(scoreFen1);
    scoreFen1.x = 290;
    scoreFen1.y = failMc1.y + 262;
    replayBtn1 = new OBJECT_ITEM("elements", null, 0, 0, 0, 0, 260, 110);
    replayBtn1.name = "btn_replay";
    replayBtn1.x = (failMc1.getWidth() - replayBtn1.getWidth()) / 2 - 10;
    replayBtn1.y = 477;
    replayBtn1.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    failMc1.addChild(replayBtn1);
    shareBtn1 = new OBJECT_ITEM("elements", null, 0, 0, 0, 110, 260, 110);
    shareBtn1.name = "btn_share";
    shareBtn1.x = replayBtn1.x;
    shareBtn1.y = replayBtn1.y + replayBtn1.getHeight() + 15;
    shareBtn1.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    failMc1.addChild(shareBtn1);
    failMc2 = new OBJECT_ITEM("result_bg", null, 0, 0, 583, 0, 583, 836);
    failMc2.x = (LGlobal.width - failMc2.getWidth()) / 2;
    failMc2.y = 42;
    failMc2.visible = false;
    resultMc.addChild(failMc2);
    scoreFen2 = new LTextField();
    scoreFen2.text = "";
    scoreFen2.font = "微软雅黑";
    scoreFen2.size = 30;
    scoreFen2.color = "#D3392C";
    scoreFen2.weight = "bolder";
    scoreFen2.textAlign = "center";
    failMc2.addChild(scoreFen2);
    scoreFen2.x = 285;
    scoreFen2.y = failMc2.y + 210;
    shareBtn2 = new OBJECT_ITEM("elements", null, 0, 0, 0, 220, 186, 62);
    shareBtn2.name = "btn_share";
    shareBtn2.x = 96;
    shareBtn2.y = 635;
    shareBtn2.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    failMc2.addChild(shareBtn2);
    replayBtn2 = new OBJECT_ITEM("elements", null, 0, 0, 0, 282, 186, 62);
    replayBtn2.name = "btn_replay";
    replayBtn2.x = 305;
    replayBtn2.y = shareBtn2.y;
    replayBtn2.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    failMc2.addChild(replayBtn2);
    successMc = new OBJECT_ITEM("result_bg", null, 0, 0, 1166, 0, 583, 836);
    successMc.x = (LGlobal.width - successMc.getWidth()) / 2;
    successMc.y = 42;
    successMc.visible = false;
    resultMc.addChild(successMc);
    shareBtn3 = new OBJECT_ITEM("elements", null, 0, 0, 0, 220, 186, 62);
    shareBtn3.name = "btn_share";
    shareBtn3.x = 96;
    shareBtn3.y = 635;
    shareBtn3.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    successMc.addChild(shareBtn3);
    replayBtn3 = new OBJECT_ITEM("elements", null, 0, 0, 0, 282, 186, 62);
    replayBtn3.name = "btn_replay";
    replayBtn3.x = 305;
    replayBtn3.y = shareBtn3.y;
    replayBtn3.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    successMc.addChild(replayBtn3);
    guaMc = new OBJECT_GUAGUA();
    closeBtn1 = new OBJECT_ITEM("result_bg", null, 0, 0, 0, 836, 80, 80);
    closeBtn1.name = "btn_close_back1";
    closeBtn1.x = failMc1.x + failMc1.getWidth() - 60;
    closeBtn1.y = failMc1.y - 15;
    closeBtn1.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    resultMc.addChild(closeBtn1);
    shareWXMc = new OBJECT_ITEM("share_tips_weixin");
    shareWXMc.name = "btn_share_weixin_back";
    shareWXMc.visible = false;
    overMc.addChild(shareWXMc);
    shareBackBtn1 = new OBJECT_ITEM("elements", null, 0, 0, 270, 282, 186, 62);
    shareBackBtn1.x = (LGlobal.width - shareBackBtn1.getWidth()) / 2;
    shareBackBtn1.y = 530;
    shareWXMc.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    shareWXMc.addChild(shareBackBtn1);
    shareWXMc.x = (LGlobal.width - shareWXMc.getWidth()) / 2;
    shareWXMc.y = 0;
    shareOtherMc = new LSprite();
    shareOtherMc.visible = false;
    overMc.addChild(shareOtherMc);
    tqqBtn = new OBJECT_ITEM("share_buttons", null, 0, 0, 0, 0, 360, 128);
    tqqBtn.name = "btn_share_tqq";
    tqqBtn.x = (LGlobal.width - tqqBtn.getWidth()) / 2;
    tqqBtn.y = 0;
    tqqBtn.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    shareOtherMc.addChild(tqqBtn);
    qzoneBtn = new OBJECT_ITEM("share_buttons", null, 0, 0, 0, 128, 360, 128);
    qzoneBtn.name = "btn_share_qzone";
    qzoneBtn.x = (LGlobal.width - qzoneBtn.getWidth()) / 2;
    qzoneBtn.y = tqqBtn.y + 128 + 30;
    qzoneBtn.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    shareOtherMc.addChild(qzoneBtn);
    weiboBtn = new OBJECT_ITEM("share_buttons", null, 0, 0, 0, 256, 360, 128);
    weiboBtn.name = "btn_share_weibo";
    weiboBtn.x = (LGlobal.width - weiboBtn.getWidth()) / 2;
    weiboBtn.y = qzoneBtn.y + 128 + 30;
    weiboBtn.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    shareOtherMc.addChild(weiboBtn);
    shareBackBtn2 = new OBJECT_ITEM("elements", null, 0, 0, 270, 282, 186, 62);
    shareBackBtn2.name = "btn_share_other_back";
    shareBackBtn2.x = (LGlobal.width - shareBackBtn2.getWidth()) / 2;
    shareBackBtn2.y = weiboBtn.y + 128 + 80;
    shareBackBtn2.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    shareOtherMc.addChild(shareBackBtn2);
    shareOtherMc.y = (LGlobal.height - shareOtherMc.getHeight()) / 2;
    soundIco = new LAnimationTimeline(new LBitmapData(imglist["audio_switch"], 0, 0, 50, 50), LGlobal.divideCoordinate(100, 50, 1, 2));
    soundIco.name = "btn_sound";
    soundIco.x = 20;
    soundIco.y = LGlobal.height - 50 - 5;
    soundIco.scaleX = 0.7;
    soundIco.scaleY = 0.7;
    soundIco.speed = 25;
    soundIco.setLabel("playmusic", 0, 0);
    soundIco.setLabel("stopmusic", 0, 1);
    soundIco.gotoAndStop("playmusic");
    soundIco.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
    soundIco.visible = false;
    soundIco.alpha = 0.5;
    controlLayer.addChild(soundIco);
    statusGame = "init";
    gameHandler("IndexIn");
    adsObj = new AdsPanel()
}
function initSoundBg() {
    soundBg = new LSound();
    var url = "sounds/bgm.";
    soundBg.load(url + "mp3");
    soundBg.addEventListener(LEvent.COMPLETE, onSoundLoadComplete)
}
function onSoundLoadComplete(e) {
    soundBg.removeEventListener(LEvent.COMPLETE, onSoundLoadComplete);
    bolSoundLoaded = true
}
function playBgMusic(t) {
    if (bolSoundLoaded) {
        if (bolSoundHadStopByUser == true) {
            soundBg.close();
            soundIco.gotoAndStop("stopmusic");
            return
        }
        if (soundBg.playing == false) {
            soundBg.setVolume(0.7);
            soundBg.play(0, 9999);
            soundIco.visible = true;
            soundIco.gotoAndStop("playmusic")
        }
    }
}
function showLoadingTips() {
    tipsTxt = new LTextField();
    tipsTxt.text = "精彩总留给等待的人！\n        等一下啦~";
    tipsTxt.x = 0;
    tipsTxt.y = 0;
    tipsTxt.font = "微软雅黑";
    tipsTxt.setMultiline(true);
    tipsTxt.setWordWrap(true, 30);
    tipsTxt.size = 16;
    tipsTxt.width = 225;
    tipsTxt.color = "#ffffff";
    addChild(tipsTxt);
    tipsTxt.x = (LGlobal.width - tipsTxt.getWidth()) / 2 + 15;
    tipsTxt.y = (LGlobal.height - tipsTxt.getHeight()) / 2 + 110
}
function setCountTimer(t, num) {
    if (t == "start") {
        var gnum = num || 0;
        timeCount = goodsTimeArr[gnum] * 10;
        countTimer = setInterval(function () {
            timeCount -= 1;
            numT2 = String(timeCount / 10);
            numT2 = numT2.indexOf(".") > 0 ? numT2 : ("" + numT2 + ".0");
            numT2 += " 秒";
            timeTxt.text = numT2;
            if (timeCount <= 0) {
                gameHandler("GameOver")
            }
        }, 100)
    } else {
        if (t == "stop") {
            clearInterval(countTimer)
        }
    }
}
function setTipsTimer(t, num) {
    if (t == "start") {
        var tnum = num || 200;
        handPic.alpha = 0;
        tipsTimer = setTimeout(function () {
            handPic.alpha = 0;
            handPic.x = xHandDefault + 40;
            handPic.y = yHandDefault + 180;
            LTweenLite.to(handPic, 0.8, {
                alpha: 1,
                x: xHandDefault,
                y: yHandDefault,
                ease: LEasing.Quart.easeOut
            }).to(handPic, 0.2, {delay: 0.5, alpha: 0, ease: LEasing.Quart.easeOut});
            setTipsTimer("start", 1900)
        }, tnum)
    } else {
        if (t == "stop") {
            clearInterval(tipsTimer)
        }
    }
}
function onScrollDown(event) {
    introText.addEventListener(LMouseEvent.MOUSE_MOVE, onScrollMove);
    numT3 = event.offsetY
}
function onScrollUp(event) {
    introText.removeEventListener(LMouseEvent.MOUSE_MOVE, onScrollMove)
}
function onScrollMove(event) {
    numT4 = event.offsetY;
    var tmpY = (numT4 - numT3);
    tmpY = introText.y + tmpY;
    LTweenLite.to(introText, 0.5, {y: tmpY});
    introText.addEventListener(LEvent.ENTER_FRAME, onScrollEnterFrame)
}
function onScrollEnterFrame() {
    if (introText.y > 56) {
        LTweenLite.to(introText, 0.1, {y: 56})
    }
    var tmpY = -320;
    if (introText.y < tmpY) {
        LTweenLite.to(introText, 0.1, {y: tmpY})
    }
}
function goodsObject(t) {
    if (t == "create") {
        goodsMc = new OBJECT_GOODS();
        goodsMc.x = goodsMc.beginX;
        goodsMc.y = goodsMc.beginY;
        goodsLayer.addChild(goodsMc);
        goodsObject("go_in")
    } else {
        if (t == "go_in") {
            if (goodsMc == null || goodsMc == "undefined") {
                return
            }
            for (var i = 0; i <= 8; i++) {
                if (goodsMc.goods[i] != null && goodsMc.goods[i] != "undefined") {
                    if (i == 8) {
                        goodsMc.goods[i].y -= 50;
                        LTweenLite.to(goodsMc.goods[i], 0.5, {
                            delay: 0.05 * i,
                            y: goodsMc.goods[i].y + 50,
                            alpha: 1,
                            ease: LEasing.Cubic.easeOut,
                            onComplete: function () {
                                if (bolGameAgain == false && goodsNO == 1) {
                                    gameHandler("TipsShow")
                                } else {
                                    if (goodsMc != null && goodsMc != "undefined") {
                                        goodsMc.clickEnabled = true
                                    }
                                    if (statusGame != "GamePlaying" && statusGame != "GameOver") {
                                        gameHandler("BeginGame")
                                    }
                                    setCountTimer("start", (goodsNO - 1))
                                }
                            }
                        })
                    } else {
                        goodsMc.goods[i].y -= 50;
                        LTweenLite.to(goodsMc.goods[i], 0.5, {
                            delay: 0.05 * i,
                            y: goodsMc.goods[i].y + 50,
                            alpha: 1,
                            ease: LEasing.Cubic.easeOut
                        })
                    }
                }
            }
        } else {
            if (t == "go_out") {
                if (goodsMc == null || goodsMc == "undefined") {
                    return
                }
                LTweenLite.to(goodsMc, 0.4, {
                    delay: 0.1,
                    y: goodsMc.endY,
                    alpha: 0,
                    ease: LEasing.Cubic.easeIn,
                    onComplete: function () {
                        if (statusGame == "GamePlaying") {
                            goodsObject("removeAndCreate")
                        }
                    }
                })
            } else {
                if (t == "removeAndCreate") {
                    goodsObject("remove");
                    goodsCurrent += 2;
                    if (goodsCurrent > 8) {
                        goodsCurrent = 1;
                        sceneCurrent += 1;
                        LTweenLite.to(bgAll, 1.2, {
                            y: -1010 * sceneCurrent,
                            ease: LEasing.Cubic.easeIn,
                            onComplete: function () {
                                if (statusGame == "GamePlaying") {
                                    goodsObject("create")
                                }
                            }
                        })
                    } else {
                        goodsObject("create")
                    }
                } else {
                    if (t == "remove") {
                        if (goodsMc == null || goodsMc == "undefined") {
                            return
                        }
                        goodsMc.clickEnabled = null;
                        goodsMc.beginX = null;
                        goodsMc.beginY = null;
                        goodsMc.endY = null;
                        goodsMc.removeAllGoods();
                        goodsMc.removeAllChild();
                        goodsMc.die();
                        goodsMc.remove();
                        goodsMc = null
                    }
                }
            }
        }
    }
}
function gameHandler(t) {
    if (t == "IndexIn") {
        snowMc.setFall("playAll");
        treeMc.play();
        gameNameMc.y = yNum1 - 300;
        gameNameMc.visible = true;
        LTweenLite.to(gameNameMc, 0.7, {delay: 0.1, y: gameNameMc.y + 300, alpha: 1, ease: LEasing.Cubic.easeOut});
        beginBtn.y = yNum2 - 200;
        beginBtn.alpha = 0;
        beginBtn.visible = true;
        LTweenLite.to(beginBtn, 0.6, {delay: 0.3, y: beginBtn.y + 200, alpha: 1, ease: LEasing.Cubic.easeOut});
        introBtn.y = yNum3 - 200;
        introBtn.alpha = 0;
        introBtn.visible = true;
        LTweenLite.to(introBtn, 0.6, {delay: 0.5, y: introBtn.y + 200, alpha: 1, ease: LEasing.Cubic.easeOut})
    } else {
        if (t == "IndexOut") {
            beginBtn.removeEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
            introBtn.removeEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
            copyrightBtn.visible = false;
            treeMc.stop();
            LTweenLite.to(gameNameMc, 0.8, {y: -gameNameMc.getHeight(), ease: LEasing.Cubic.easeIn});
            LTweenLite.to(beginBtn, 0.8, {delay: 0.2, y: -beginBtn.getHeight(), ease: LEasing.Cubic.easeIn});
            LTweenLite.to(introBtn, 0.8, {delay: 0.4, y: -introBtn.getHeight(), ease: LEasing.Cubic.easeIn});
            LTweenLite.to(bgAll, 1.5, {delay: 0.3, y: -1010 * (sceneCurrent + 1), ease: LEasing.Cubic.easeIn});
            topUI.y = -260;
            topUI.visible = true;
            LTweenLite.to(topUI, 0.6, {
                delay: 1.7, y: 0, ease: LEasing.Back.easeOut, onComplete: function () {
                    gameNameMc.visible = false;
                    beginBtn.visible = false;
                    introBtn.visible = false;
                    gameHandler("GameReady")
                }
            })
        } else {
            if (t == "GameReady") {
                bolGameSuccess = false;
                dKey = "";
                dCode = "";
                arryKey = [];
                arryCode = [];
                sceneCurrent = 1;
                goodsCurrent = 1;
                goodsCollectCount = 0;
                goodsNO = 1;
                timeCount = goodsTimeArr[goodsNO - 1] * 10;
                goodsObject("create")
            } else {
                if (t == "BeginGame") {
                    statusGame = "GamePlaying";
                    setTipsTimer("stop");
                    bgBlack.visible = false;
                    tipsMc.visible = false
                } else {
                    if (t == "GameOver") {
                        statusGame = "GameOver";
                        setCountTimer("stop");
                        timeTxt.text = "0.0 秒";
                        if (goodsMc != null && goodsMc != "undefined") {
                            goodsMc.clickEnabled = false
                        }
                        bolHadSubmit = false;
                        if (bolGameSuccess == true) {
                            if (goodsCollectCount >= 16) {
                                guaMc.addToResultMc("success")
                            } else {
                                guaMc.addToResultMc("fail")
                            }
                            guaMc.loadingMc.playMovie("start");
                            formatData();
                            submiDataLottery()
                        }
                        bgBlack.alpha = 0;
                        bgBlack.visible = true;
                        LTweenLite.to(bgBlack, 0.5, {
                            delay: 0.5,
                            alpha: 0.7,
                            ease: LEasing.Quart.easeOut,
                            onComplete: function () {
                                gameHandler("ShowResult")
                            }
                        });
                        _hmt.push(["_trackPageview", "/games/christmas/game/game_over"])
                    } else {
                        if (t == "ShowResult") {
                            overMc.visible = true;
                            resultMc.visible = true;
                            tipsNo.visible = false;
                            shareData["title"] = "【丢失的圣诞礼物】脑洞大开也只找到" + goodsCollectCount + "个礼物";
                            shareData2["desc"] = "【丢失的圣诞礼物】脑洞大开也只找到" + goodsCollectCount + "个礼物";
                            if (bolGameSuccess == true) {
                                if (goodsCollectCount >= 16) {
                                    failMc1.visible = false;
                                    failMc2.visible = false;
                                    successMc.visible = true;
                                    gResult = goodsCollectCount;
                                    shareData["title"] = "【丢失的圣诞礼物】16个礼物全部找回，圣诞老人都给你跪了！";
                                    shareData2["desc"] = "【丢失的圣诞礼物】16个礼物全部找回，圣诞老人都给你跪了！"
                                } else {
                                    scoreFen2.text = goodsCollectCount;
                                    failMc1.visible = false;
                                    failMc2.visible = true;
                                    successMc.visible = false;
                                    gResult = goodsCollectCount
                                }
                            } else {
                                scoreFen1.text = goodsCollectCount;
                                failMc1.visible = true;
                                failMc2.visible = false;
                                successMc.visible = false;
                                gResult = goodsCollectCount
                            }
                            snowMc.setFall("reset");
                            LTweenLite.removeAll();
                            snowMc.setFall("playAll");
                            goodsObject("remove");
                            bgAll.y = -1010 * sceneCurrent;
                            initVal();
                            adsObj.displayAds("show");
                            _hmt.push(["_trackPageview", "/games/christmas/game/game_over/result_" + gResult])
                        } else {
                            if (t == "ShowShare") {
                                resultMc.visible = false;
                                if (checkBrowserWeiXin()) {
                                    shareWXMc.visible = true
                                } else {
                                    shareOtherMc.visible = true
                                }
                                adsObj.displayAds("hidden")
                            } else {
                                if (t == "ShowShare_Back") {
                                    shareWXMc.visible = false;
                                    shareOtherMc.visible = false;
                                    resultMc.visible = true;
                                    adsObj.displayAds("show")
                                } else {
                                    if (t == "TipsShow") {
                                        tipsMc.visible = true;
                                        setTipsTimer("start")
                                    } else {
                                        if (t == "TipsClick") {
                                            gameHandler("BeginGame");
                                            goodsMc.showNextGoods()
                                        } else {
                                            if (t == "ReplayGame") {
                                                guaMc.eraseMc.removeEventListener(LMouseEvent.MOUSE_DOWN, guaMc.onEraseStart);
                                                guaMc.eraseMc.removeEventListener(LMouseEvent.MOUSE_MOVE, guaMc.onEraseMove);
                                                LGlobal.stage.removeEventListener(LMouseEvent.MOUSE_UP, guaMc.onEraseEnd);
                                                bgAll.y = -1010;
                                                topUI.removeAllIcos();
                                                timeTxt.text = timeCount / 10 + ".0 秒";
                                                statusGame = "ReplayGame";
                                                bgBlack.visible = false;
                                                overMc.visible = false;
                                                failMc1.visible = false;
                                                successMc.visible = false;
                                                bolGameAgain = true;
                                                setTimeout(function () {
                                                    gameHandler("GameReady")
                                                }, 500);
                                                adsObj.displayAds("hidden")
                                            } else {
                                                if (t == "ShowIntro") {
                                                    bgBlack.alpha = 0.7;
                                                    bgBlack.visible = true;
                                                    introMc.visible = true;
                                                    introText.y = 150;
                                                    LTweenLite.to(introText, 0.8, {
                                                        y: 56,
                                                        ease: LEasing.Quart.easeOut,
                                                        onComplete: function () {
                                                            introText.addEventListener(LMouseEvent.MOUSE_DOWN, onScrollDown);
                                                            introText.addEventListener(LMouseEvent.MOUSE_UP, onScrollUp);
                                                            LGlobal.stage.addEventListener(LMouseEvent.MOUSE_UP, onScrollUp)
                                                        }
                                                    })
                                                } else {
                                                    if (t == "HiddenIntro") {
                                                        bgBlack.visible = false;
                                                        introMc.visible = false;
                                                        introText.removeEventListener(LMouseEvent.MOUSE_DOWN, onScrollDown);
                                                        introText.removeEventListener(LMouseEvent.MOUSE_UP, onScrollUp);
                                                        introText.removeEventListener(LMouseEvent.MOUSE_MOVE, onScrollMove);
                                                        LGlobal.stage.removeEventListener(LMouseEvent.MOUSE_UP, onScrollUp)
                                                    } else {
                                                        if (t == "BackToHome") {
                                                            guaMc.eraseMc.removeEventListener(LMouseEvent.MOUSE_DOWN, guaMc.onEraseStart);
                                                            guaMc.eraseMc.removeEventListener(LMouseEvent.MOUSE_MOVE, guaMc.onEraseMove);
                                                            LGlobal.stage.removeEventListener(LMouseEvent.MOUSE_UP, guaMc.onEraseEnd);
                                                            beginBtn.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
                                                            introBtn.addEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
                                                            bgAll.y = 0;
                                                            topUI.visible = false;
                                                            topUI.removeAllIcos();
                                                            timeTxt.text = timeCount / 10 + ".0 秒";
                                                            statusGame = "BackToHome";
                                                            bgBlack.visible = false;
                                                            overMc.visible = false;
                                                            failMc1.visible = false;
                                                            successMc.visible = false;
                                                            tipsNo.visible = false;
                                                            bolGameAgain = true;
                                                            gameHandler("IndexIn");
                                                            adsObj.displayAds("hidden")
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
function onButtonClickHandler(e) {
    switch (e.currentTarget.name) {
        case"btn_begin":
            gameHandler("IndexOut");
            _hmt.push(["_trackPageview", "/games/christmas/button/game_start"]);
            break;
        case"btn_intro":
            gameHandler("ShowIntro");
            _hmt.push(["_trackPageview", "/games/christmas/button/game_intro"]);
            break;
        case"btn_close_intro":
            gameHandler("HiddenIntro");
            break;
        case"btn_copyright":
            bgBlack.visible = true;
            copyrightMc.visible = true;
            return;
            break;
        case"btn_copyright_close":
            bgBlack.visible = false;
            copyrightMc.visible = false;
            return;
            break;
        case"tips_mc":
            tipsMc.removeEventListener(LMouseEvent.MOUSE_DOWN, onButtonClickHandler);
            gameHandler("TipsClick");
            break;
        case"btn_sound":
            bolSoundHadStopByUser = bolSoundHadStopByUser ? false : true;
            _hmt.push(["_trackPageview", "/games/christmas/button/game_sound/soundstop_" + bolSoundHadStopByUser]);
            break;
        case"btn_replay":
            gameHandler("ReplayGame");
            abortAjax();
            _hmt.push(["_trackPageview", "/games/christmas/button/game_replay"]);
            break;
        case"btn_close_back1":
            gameHandler("BackToHome");
            abortAjax();
            _hmt.push(["_trackPageview", "/games/christmas/button/back_to_home"]);
            break;
        case"btn_share":
            gameHandler("ShowShare");
            _hmt.push(["_trackPageview", "/games/christmas/button/game_share"]);
            break;
        case"btn_share_weixin_back":
            gameHandler("ShowShare_Back");
            break;
        case"btn_share_other_back":
            gameHandler("ShowShare_Back");
            break;
        case"btn_share_tqq":
            _hmt.push(["_trackPageview", "/games/christmas/share/qweibo"]);
            shareSNS = getShareURL(shareData["link"], shareData["title"], shareData["imgUrl"]);
            window.location.href = shareSNS.tqq;
            break;
        case"btn_share_qzone":
            _hmt.push(["_trackPageview", "/games/christmas/share/qzone"]);
            shareSNS = getShareURL(shareData["link"], shareData["title"], shareData["imgUrl"]);
            window.location.href = shareSNS.qzone;
            break;
        case"btn_share_weibo":
            _hmt.push(["_trackPageview", "/games/christmas/share/weibo"]);
            shareSNS = getShareURL(shareData["link"], shareData["title"], shareData["imgUrl"]);
            window.location.href = shareSNS.weibo;
            break
    }
    windowResizeListener("add");
    playBgMusic()
}
function OBJECT_SNOW() {
    LExtends(this, LSprite, []);
    var self = this;
    var s1 = new OBJECT_ITEM("wu_snow");
    var s2 = s1.clone();
    s2.y = -1010;
    this.snow1 = new LSprite();
    this.snow1.addChild(s1);
    this.snow1.addChild(s2);
    this.snow1.alpha = 0.3;
    this.snow2 = this.snow1.clone();
    this.snow2.alpha = 1;
    this.snow3 = this.snow1.clone();
    this.snow3.alpha = 0.4;
    self.addChild(this.snow1);
    self.addChild(this.snow2);
    self.addChild(this.snow3);
    this.isPlaying = false;
    this.tween1 = null;
    this.tween2 = null;
    this.tween3 = null
}
OBJECT_SNOW.prototype.setFall = function (t) {
    var self = this;
    if (t == "playAll") {
        if (this.isPlaying == false) {
            this.isPlaying = true;
            setTimeout(function () {
                self.setFall("play1");
                self.setFall("play2");
                self.setFall("play3")
            }, 200)
        }
    } else {
        if (t == "play1") {
            this.snow1.y = 0;
            this.tween1 = LTweenLite.to(this.snow1, 50, {
                y: 1010,
                ease: LEasing.None.easeInOut,
                onComplete: function () {
                    self.setFall("play1")
                }
            })
        } else {
            if (t == "play2") {
                this.snow2.y = 0;
                this.tween2 = LTweenLite.to(this.snow2, 30, {
                    y: 1010,
                    ease: LEasing.None.easeInOut,
                    onComplete: function () {
                        self.setFall("play2")
                    }
                })
            } else {
                if (t == "play3") {
                    this.snow3.y = 0;
                    this.tween3 = LTweenLite.to(this.snow3, 10, {
                        y: 1010,
                        ease: LEasing.None.easeInOut,
                        onComplete: function () {
                            self.setFall("play3")
                        }
                    })
                } else {
                    if (t == "reset") {
                        LTweenLite.remove(this.tween1);
                        LTweenLite.remove(this.tween2);
                        LTweenLite.remove(this.tween3);
                        this.snow1.y = 0;
                        this.snow2.y = 0;
                        this.snow3.y = 0;
                        this.isPlaying = false
                    }
                }
            }
        }
    }
};
function OBJECT_GOODS() {
    LExtends(this, LSprite, []);
    var self = this;
    var m = 0, n = 0;
    var num, count;
    this.clickEnabled = false;
    this.beginX = 19;
    this.beginY = 267;
    this.endY = 210;
    this.goods = [];
    var g = new OBJECT_ITEM("wu_goods", null, 0, 0, (203 * (goodsCurrent - 1)), 192 * (sceneCurrent - 1), 203, 192);
    g.correct = 0;
    var g2;
    if (bolGameAgain == false && goodsNO == 1) {
        for (n = 0; n <= 8; n++) {
            if (n == 3) {
                g2 = new OBJECT_ITEM("wu_goods", null, 0, 0, (203 * (goodsCurrent - 1 + 1)), 192 * (sceneCurrent - 1), 203, 192);
                g2.correct = goodsNO;
                this.goods.push(g2);
                n += 1
            }
            var g1 = g.clone();
            this.goods.push(g1)
        }
    } else {
        for (n = 0; n <= 7; n++) {
            var g1 = g.clone();
            this.goods.push(g1)
        }
        g2 = new OBJECT_ITEM("wu_goods", null, 0, 0, (203 * (goodsCurrent - 1 + 1)), 192 * (sceneCurrent - 1), 203, 192);
        g2.correct = goodsNO;
        this.goods.push(g2);
        count = this.goods.length;
        while (count--) {
            num = Math.round(Math.random() * count);
            this.goods[count] = [this.goods[num], this.goods[num] = this.goods[count]][0]
        }
    }
    for (var i = 0; i <= 2; i++) {
        for (var j = 0; j <= 2; j++) {
            if (this.goods[m].correct != 0) {
                this.goods[m].yes = m + 1
            } else {
                this.goods[m].yes = 0
            }
            this.goods[m].iid = m + 1;
            this.goods[m].x = 203 * j;
            this.goods[m].y = 192 * i;
            this.goods[m].alpha = 0;
            this.goods[m].addEventListener(LMouseEvent.MOUSE_DOWN, this.onGoodsClick);
            self.addChild(this.goods[m]);
            m += 1
        }
    }
}
OBJECT_GOODS.prototype.onGoodsClick = function (e) {
    var icon = e.currentTarget;
    var mc = e.currentTarget.parent;
    if (mc.clickEnabled) {
        mc.clickEnabled = false;
        if (icon.correct != 0) {
            arryKey.push(icon.yes);
            arryCode.push(icon.yes);
            setCountTimer("stop");
            mc.flyIcon(icon, mc.beginX, mc.beginY)
        } else {
            tipsNo.x = -tipsNo.getWidth();
            tipsNo.visible = true;
            LTweenLite.to(tipsNo, 0.6, {
                x: xTipsNo, ease: LEasing.Back.easeOut, onComplete: function () {
                    LTweenLite.to(tipsNo, 0.5, {
                        delay: 0.4,
                        x: LGlobal.width,
                        ease: LEasing.Back.easeIn,
                        onComplete: function () {
                            mc.clickEnabled = true;
                            tipsNo.visible = false
                        }
                    })
                }
            })
        }
    }
};
OBJECT_GOODS.prototype.showNextGoods = function () {
    arryKey.push(4);
    arryCode.push(4);
    this.flyIcon(this.goods[3], this.beginX, this.beginY)
};
OBJECT_GOODS.prototype.flyIcon = function (icon, xnum, ynum) {
    var icon2 = topUI.addIco(icon.correct);
    topUI.addChild(icon);
    icon.x += xnum;
    icon.y += ynum;
    LTweenLite.to(icon, 0.7, {
        x: icon2.x - 30,
        y: icon2.y - 30,
        scaleX: 0.4,
        scaleY: 0.4,
        ease: LEasing.Back.easeIn,
        onComplete: function () {
            icon.remove();
            goodsCollectCount += 1;
            if (goodsNO == 3) {
                bolGameSuccess = true
            }
            goodsNO += 1;
            if (goodsNO == 17) {
                gameHandler("GameOver")
            } else {
                goodsObject("go_out")
            }
        }
    })
};
OBJECT_GOODS.prototype.removeAllGoods = function () {
    for (var n = 0; n <= 8; n++) {
        this.goods[n].removeAllChild();
        this.goods[n].yes = null;
        this.goods[n].iid = null;
        this.goods[n].correct = null;
        this.goods[n].die();
        this.goods[n].remove();
        this.goods[n] = null
    }
    this.goods = null
};
function UITop() {
    LExtends(this, LSprite, []);
    var self = this;
    var bgTop = new OBJECT_ITEM("ui_topbg");
    bgTop.x = -90;
    bgTop.y = -75;
    self.addChild(bgTop);
    this.collectNum = 0;
    this.icoBox = new LSprite();
    this.icoBox.x = 11;
    this.icoBox.y = 11;
    self.addChild(this.icoBox);
    this.tween = null
}
UITop.prototype.addIco = function (num) {
    var row, ver;
    if (num >= 9) {
        row = num - 9;
        ver = 1
    } else {
        row = num - 1;
        ver = 0
    }
    var ico = new OBJECT_ITEM("ui_topgoods", "centerXY", 0, 0, 77.25 * row, 84 * ver, 77.25, 84);
    ico.x = 77.25 * row + 77.25 / 2;
    ico.y = 84 * ver + 84 / 2;
    this.icoBox.addChild(ico);
    ico.alpha = 0;
    ico.scaleX = 0.1;
    ico.scaleY = 0.1;
    this.tween = LTweenLite.to(ico, 0.6, {delay: 0.7, scaleX: 1, scaleY: 1, alpha: 1, ease: LEasing.Back.easeOut});
    return ico
};
UITop.prototype.removeAllIcos = function () {
    this.icoBox.removeAllChild();
    this.tween = null
};
function OBJECT_GUAGUA() {
    LExtends(this, LSprite, []);
    var self = this;
    this.awardNum = 0;
    this.eraseStatus = 0;
    this.eraseX1 = 0;
    this.eraseY1 = 0;
    this.eraseX2 = 0;
    this.eraseY2 = 0;
    this.awardBitmapData = null;
    this.awardMc = new LSprite();
    self.addChild(this.awardMc);
    this.inputBtn = new OBJECT_ITEM("elements", null, 0, 0, 0, 350, 173, 44);
    this.inputBtn.x = 232;
    this.inputBtn.y = 152;
    this.inputBtn.addEventListener(LMouseEvent.MOUSE_DOWN, this.onBtnInputClick);
    this.inputBtn.visible = false;
    self.addChild(this.inputBtn);
    this.eraseMc = new LSprite();
    this.eraseBitmapData = new LBitmapData(imglist["result_info"], 0, 0, 444, 218);
    this.eraseMc.graphics.beginBitmapFill(this.eraseBitmapData);
    this.eraseMc.graphics.drawRect(1, "#000000", [0, 0, 444, 218]);
    this.eraseMc.visible = true;
    self.addChildAt(this.eraseMc, 2);
    this.loadingMc = new LoadingIcon(444, 218);
    this.loadingMc.x = 0;
    this.loadingMc.y = 0;
    this.loadingMc.visible = false;
    self.addChild(this.loadingMc);
    this.inputMc = new OBJECT_ITEM("result_info", null, 0, 0, 444 * 9, 0, 444, 218);
    this.inputMc.x = 0;
    this.inputMc.y = 0;
    this.inputMc.visible = false;
    self.addChild(this.inputMc);
    this.userTxt = new LTextField();
    this.userTxt.text = "请输入姓名";
    this.userTxt.x = 133;
    this.userTxt.y = 25;
    this.userTxt.font = "微软雅黑";
    this.userTxt.size = 20;
    this.userTxt.color = "#044d7a";
    this.userTxt.width = 240;
    this.userTxt.height = 45;
    var inputBg1 = new LSprite();
    inputBg1.graphics.drawRect(0, "#000000", [0, 0, 240, 45]);
    this.userTxt.setType(LTextFieldType.INPUT, inputBg1);
    this.userTxt.addEventListener(LFocusEvent.FOCUS_IN, function () {
        windowResizeListener("remove");
        if (self.userTxt.text == "请输入姓名") {
            self.userTxt.text = ""
        }
    });
    this.inputMc.addChild(this.userTxt);
    this.phoneTxt = new LTextField();
    this.phoneTxt.text = "请输入手机号";
    this.phoneTxt.x = 133;
    this.phoneTxt.y = 87;
    this.phoneTxt.font = "微软雅黑";
    this.phoneTxt.size = 20;
    this.phoneTxt.color = "#044d7a";
    this.phoneTxt.width = 240;
    this.phoneTxt.height = 45;
    var inputBg2 = new LSprite();
    inputBg2.graphics.drawRect(0, "#000000", [0, 0, 240, 45]);
    this.phoneTxt.setType(LTextFieldType.INPUT, inputBg2);
    this.phoneTxt.addEventListener(LFocusEvent.FOCUS_IN, function () {
        windowResizeListener("remove");
        if (self.phoneTxt.text == "请输入手机号") {
            self.phoneTxt.text = ""
        }
    });
    this.inputMc.addChild(this.phoneTxt);
    this.submitBtn = new OBJECT_ITEM("elements", null, 0, 0, 270, 220, 210, 58);
    this.submitBtn.name = "btn_input_submit";
    this.submitBtn.x = 123;
    this.submitBtn.y = 140;
    this.submitBtn.addEventListener(LMouseEvent.MOUSE_DOWN, this.onBtnSubmitClick);
    this.inputMc.addChild(this.submitBtn)
}
OBJECT_GUAGUA.prototype.onBtnInputClick = function (e) {
    if (bolHadSubmit == false) {
        guaMc.inputBtn.visible = false;
        guaMc.inputMc.visible = true
    } else {
        alert("您已提交过资料了。")
    }
    _hmt.push(["_trackPageview", "/games/christmas/button/data_input"])
};
OBJECT_GUAGUA.prototype.onBtnSubmitClick = function (e) {
    submiDataUser();
    windowResizeListener("add");
    _hmt.push(["_trackPageview", "/games/christmas/button/data_submit"])
};
OBJECT_GUAGUA.prototype.gotoBack = function () {
    guaMc.inputBtn.visible = true;
    guaMc.inputMc.visible = false;
    windowResizeListener("add")
};
OBJECT_GUAGUA.prototype.addToResultMc = function (t) {
    this.awardMc.removeAllChild();
    this.resetGG();
    if (t == "fail") {
        guaMc.x = 70;
        guaMc.y = 395;
        failMc2.addChild(guaMc)
    } else {
        if (t == "success") {
            guaMc.x = 70;
            guaMc.y = 398;
            successMc.addChild(guaMc)
        }
    }
};
OBJECT_GUAGUA.prototype.resetGG = function () {
    this.awardNum = 0;
    this.eraseStatus = 0;
    this.eraseX1 = 0;
    this.eraseY1 = 0;
    this.eraseX2 = 0;
    this.eraseY2 = 0;
    this.userTxt.text = "请输入姓名";
    this.phoneTxt.text = "请输入手机号";
    this.inputBtn.visible = false;
    this.inputMc.visible = false;
    this.eraseMc.remove();
    this.eraseMc = null;
    this.eraseMc = new LSprite();
    this.eraseMc.graphics.beginBitmapFill(this.eraseBitmapData);
    this.eraseMc.graphics.drawRect(1, "#000000", [0, 0, 444, 218]);
    this.eraseMc.visible = true;
    this.addChildAt(this.eraseMc, 2)
};
OBJECT_GUAGUA.prototype.readyAward = function (num) {
    this.awardNum = num;
    this.awardMc.removeAllChild();
    var pic;
    if (this.awardNum == 1 || this.awardNum == 2) {
        pic = new OBJECT_ITEM("result_info", null, 0, 0, 444 * (this.awardNum + 6), 0, 444, 218)
    } else {
        pic = new OBJECT_ITEM("result_info", null, 0, 0, 444 * (Math.floor(Math.random() * 6) + 1), 0, 444, 218)
    }
    this.awardMc.addChild(pic);
    this.awardBitmapData = new LBitmapData(null, 0, 0, 444, 218);
    this.awardBitmapData.draw(this.awardMc);
    this.eraseMc.addEventListener(LMouseEvent.MOUSE_DOWN, this.onEraseStart);
    LGlobal.stage.addEventListener(LMouseEvent.MOUSE_UP, this.onEraseEnd)
};
OBJECT_GUAGUA.prototype.onEraseStart = function (e) {
    guaMc.eraseStatus = 1;
    guaMc.eraseX1 = e.selfX;
    guaMc.eraseY1 = e.selfY;
    guaMc.eraseMc.graphics.beginBitmapFill(guaMc.awardBitmapData);
    guaMc.eraseMc.graphics.drawArc(0, "#000000", [guaMc.eraseX1, guaMc.eraseY1, 30, 0, Math.PI * 2]);
    guaMc.eraseMc.addEventListener(LMouseEvent.MOUSE_MOVE, guaMc.onEraseMove);
    _hmt.push(["_trackPageview", "/games/christmas/button/guagua"])
};
OBJECT_GUAGUA.prototype.onEraseMove = function (e) {
    guaMc.eraseX2 = e.selfX;
    guaMc.eraseY2 = e.selfY;
    guaMc.eraseMc.graphics.beginBitmapFill(guaMc.awardBitmapData);
    guaMc.eraseMc.graphics.drawArc(0, "#000000", [guaMc.eraseX2, guaMc.eraseY2, 30, 0, Math.PI * 2])
};
OBJECT_GUAGUA.prototype.onEraseEnd = function (e) {
    if (guaMc.eraseStatus == 1) {
        guaMc.eraseStatus = 0;
        guaMc.eraseMc.removeEventListener(LMouseEvent.MOUSE_DOWN, guaMc.onEraseStart);
        guaMc.eraseMc.removeEventListener(LMouseEvent.MOUSE_MOVE, guaMc.onEraseMove);
        LGlobal.stage.removeEventListener(LMouseEvent.MOUSE_UP, guaMc.onEraseEnd);
        LTweenLite.to(guaMc.eraseMc, 0.2, {
            alpha: 0, onComplete: function () {
                guaMc.eraseMc.visible = false;
                if (guaMc.awardNum == 1 || guaMc.awardNum == 2) {
                    guaMc.inputBtn.visible = true
                }
            }
        })
    }
};
function OBJECT_ITEM(img, imgorigin, x, y, imgx, imgy, imgwidth, imgheight) {
    LExtends(this, LSprite, []);
    var self = this;
    var bitmap;
    if (imgx != "undefined") {
        bitmap = new LBitmap(new LBitmapData(imglist[img], imgx, imgy, imgwidth, imgheight))
    } else {
        bitmap = new LBitmap(new LBitmapData(imglist[img]))
    }
    bitmap.x = x || 0;
    bitmap.y = y || 0;
    if (imgorigin == "centerXY") {
        bitmap.x = -bitmap.getWidth() / 2;
        bitmap.y = -bitmap.getHeight() / 2
    } else {
        if (imgorigin == "centerX") {
            bitmap.x = -bitmap.getWidth() / 2
        } else {
            if (imgorigin == "centerY") {
                bitmap.y = -bitmap.getHeight() / 2
            }
        }
    }
    self.addChild(bitmap)
}
function LoadingIcon(w, h) {
    LExtends(this, LSprite, []);
    var s = this;
    s.bg = new LSprite();
    s.bg.graphics.drawRect(0, "#000000", [5, 5, (w - 10), (h - 10)], true, "#000000");
    s.bg.alpha = 0.5;
    s.addChild(s.bg);
    s.arc = new LSprite();
    s.arc.x = w / 2;
    s.arc.y = h / 2 - 20;
    s.addChild(s.arc);
    var r = 25;
    for (var i = 0; i < 360; i += 30) {
        var child = new LSprite();
        child.graphics.drawArc(0, "#FFFFFF", [r, 0, 5, 0, 2 * Math.PI], true, "#FFFFFF");
        child.rotate = i;
        child.alpha = 0.2 + i / 360;
        s.arc.addChild(child)
    }
    s.index = 0;
    s.max = 3;
    s.progress = 0;
    s.label = new LTextField();
    s.label.text = "惊喜读取中，请稍候...";
    s.label.font = "微软雅黑";
    s.label.color = "#FFFFFF";
    s.label.weight = "bolder";
    s.label.size = 16;
    s.label.textAlign = "center";
    s.label.x = s.arc.x + 8;
    s.label.y = s.arc.y + s.label.getHeight() + 20;
    s.addChild(s.label);
    var shadow = new LDropShadowFilter(0, 0, "#FFFFFF", 30);
    s.arc.filters = [shadow];
    s.label.filters = [shadow]
}
LoadingIcon.prototype.playMovie = function (t) {
    var s = this;
    if (t == "start") {
        s.addEventListener(LEvent.ENTER_FRAME, s.onframe);
        s.visible = true
    } else {
        if (t == "stop") {
            s.removeEventListener(LEvent.ENTER_FRAME, s.onframe);
            s.visible = false
        }
    }
};
LoadingIcon.prototype.onframe = function (event) {
    var s = event.target;
    if (s.index++ < s.max) {
        return
    }
    s.index = 0;
    s.arc.rotate += 30
};
function AdsPanel() {
    adsLayer = new LSprite();
    adsLayer.visible = false;
    overMc.addChildAt(adsLayer, 0);
    adsMc = new LAds(function (o) {
        _hmt.push(["_trackPageview", "/games/christmas/ads/" + o.name]);
        window.location.href = o.url
    }, 600);
    adsMc.x = (LGlobal.width - adsMc.getWidth()) * 0.5;
    adsMc.y = LGlobal.height - adsMc.getHeight() - 10;
    adsLayer.addChild(adsMc)
}
AdsPanel.prototype.displayAds = function (t) {
    if (t == "show") {
        adsLayer.visible = true;
        adsMc.setPause(false)
    } else {
        if (t == "hidden") {
            adsMc.setPause(true);
            adsLayer.visible = false
        }
    }
};
function formatData() {
    var str = "abcdefghijklmnop";
    dKey = arryKey.join("");
    for (var i = 0; i < arryKey.length; i++) {
        dKey = dKey.replace(arryKey[i], str.substr(arryKey[i] - 1, 1))
    }
    dCode = str.substr(Math.floor(Math.random() * 16), 1) + "" + arryCode.join("")
}
function getShareURL(a, b, c) {
    c = ("" || c);
    var s = {};
    s.weibo = "http://v.t.sina.com.cn/share/share.php?c=spr_web_bd_youku_weibo&url=" + encodeURIComponent(a) + "&title=" + encodeURIComponent(b) + "&pic=" + encodeURIComponent(c);
    s.renren = "http://widget.renren.com/dialog/share?link=" + encodeURIComponent(a) + "&title=" + encodeURIComponent(b) + "&image_src=" + encodeURIComponent(c);
    s.tqq = "http://v.t.qq.com/share/share.php?url=" + encodeURIComponent(a) + "&title=" + encodeURIComponent(b) + "&pic=" + encodeURIComponent(c);
    s.kaixin = "http://www.kaixin001.com/repaste/share.php?rurl=" + encodeURIComponent(a) + "&rcontent=" + encodeURIComponent(a) + "&rtitle=" + encodeURIComponent(b);
    s.feixin = "http://space.fetion.com.cn/api/share?source=%E4%BC%98%E9%85%B7%E7%BD%91&url=" + encodeURIComponent(a) + "&title=" + encodeURIComponent(b);
    s.w193 = "http://www.139.com/share/share.php?tl=953010001&source=shareto139_youku&url=" + encodeURIComponent(a) + "&title=" + encodeURIComponent(b);
    s.baidu = "http://tieba.baidu.com/i/app/open_share_api?title=" + encodeURIComponent(b) + "&link=" + encodeURIComponent(a);
    s.qzone = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=" + encodeURIComponent(a) + "&title=" + encodeURIComponent(b) + "&pics=" + encodeURIComponent(c);
    s.douban = "http://www.douban.com/share/service?text=&href=" + encodeURIComponent(a) + "&name=" + encodeURIComponent(b) + "&image=" + encodeURIComponent(c);
    s.pengyou = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?to=pengyou&url=" + encodeURIComponent(a) + "&title=" + encodeURIComponent(b) + "&pics=" + encodeURIComponent(c);
    s.qq = "http://connect.qq.com/widget/shareqq/index.html?url=" + encodeURIComponent(a) + "&title=" + encodeURIComponent(b) + "&pics=" + encodeURIComponent(c) + "&site=%E4%BC%98%E9%85%B7";
    s.blog163 = "http://t.163.com/article/user/checkLogin.do?link=" + encodeURIComponent(a) + "&info=" + encodeURIComponent(b) + "&images=" + encodeURIComponent(c) + "&source=%E4%BC%98%E9%85%B7%E7%BD%91";
    return s
}
function checkBrowserWeiXin() {
    var ua = window.navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == "micromessenger") {
        return true
    } else {
        return false
    }
}
function abortAjax() {
    if (xhrAjax && xhrAjax.readyState != 4) {
        xhrAjax.abort()
    }
}
function submiDataLottery() {
    xhrAjax = $.ajax({
        type: "POST",
        url: "http://project.youku.com/api/christmas/lottery.php",
        dataType: "json",
        data: "num=" + goodsCollectCount + "&key=" + dKey + "&code=" + dCode + "",
        success: function (data) {
            if (data.status == 0) {
                dAward = data.award;
                dRCode = data.rcode;
                dDID = data.id
            } else {
                dAward = 0;
                dRCode = "";
                dDID = ""
            }
            guaMc.readyAward(dAward);
            guaMc.loadingMc.playMovie("stop")
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            dAward = 0;
            dRCode = "";
            dDID = "";
            guaMc.readyAward(dAward);
            guaMc.loadingMc.playMovie("stop")
        }
    })
}
function submiDataUser() {
    if (bolSubmiting == false) {
        bolSubmiting = true;
        var a1 = guaMc.userTxt.text;
        var a2 = guaMc.phoneTxt.text;
        if (a1 == "" || a1 == "请输入姓名") {
            bolSubmiting = false;
            alert("请先输入您的姓名。");
            return false
        }
        if (a2 == "" || a2 == "请输入手机号") {
            bolSubmiting = false;
            alert("请先输入您的手机号。");
            return false
        }
        $.ajax({
            type: "POST",
            url: "http://project.youku.com/api/christmas/submit_u.php",
            dataType: "json",
            data: "name=" + a1 + "&phone=" + a2 + "&rcode=" + dRCode + "&id=" + dDID + "",
            success: function (data) {
                bolSubmiting = false;
                if (data.status == 0) {
                    bolHadSubmit = true;
                    alert("提交成功，感谢您的参与！");
                    guaMc.gotoBack()
                } else {
                    if (data.status == -1) {
                        alert(data.msg)
                    } else {
                        alert("系统繁忙，请稍候再试！")
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                bolSubmiting = false;
                alert("系统繁忙，稍候再试！！")
            }
        })
    }
}
function windowResizeListener(t) {
    if (t == "add") {
        if (bolHadResizeEvent == false) {
            LGlobal.stage.addEventListener(LEvent.WINDOW_RESIZE, onResizeWindow);
            bolHadResizeEvent = true
        }
    } else {
        if (t == "remove") {
            LGlobal.stage.removeEventListener(LEvent.WINDOW_RESIZE, onResizeWindow);
            bolHadResizeEvent = false
        }
    }
}
function onResizeWindow() {
    getScreenWH();
    LGlobal.resize(gameScreenWidth, gameScreenHeight);
    LGlobal.align = LStageAlign.TOP_MIDDLE;
    LGlobal.stageScale = LStageScaleMode.SHOW_ALL;
    LSystem.screen(LStage.FULL_SCREEN)
}
function getScreenWH() {
    if (LGlobal.mobile) {
        gameScreenWidth = 640;
        gameScreenHeight = 1010
    } else {
        gameScreenWidth = 640;
        gameScreenHeight = 1010
    }
}
$(function ($) {
    getScreenWH();
    LInit(30, "gameContainer", gameScreenWidth, gameScreenHeight, loadResource);
    var rrr = Math.floor(Math.random() * 29) + 2;
    shareData = {
        "appId": "",
        "imgUrl": "http://r2.ykimg.com/051000005491631D6737B34B0103546B",
        "link": "http://www.laibican.com/games/yk-gifts.html",
        "desc": "找回全部圣诞礼物谈何容易！不信？你行你上啊！",
        "title": "丢失的圣诞礼物"
    };
    shareData2 = {
        "appId": "",
        "imgUrl": "http://r2.ykimg.com/051000005491631D6737B34B0103546B",
        "link": "http://www.laibican.com/games/yk-gifts.html",
        "desc": "丢失的圣诞礼物",
        "title": "找回全部圣诞礼物谈何容易！不信？你行你上啊！"
    };
    WeixinApi.ready(function (Api) {
        var wxCallbacks1 = {
            all: function (resp) {
                _hmt.push(["_trackPageview", "/games/christmas/share/weixin/sharetofriend"]);
                shareWXMc.visible = false;
                resultMc.visible = true
            }
        };
        var wxCallbacks2 = {
            all: function (resp) {
                _hmt.push(["_trackPageview", "/games/christmas/share/weixin/sharetotimeline"]);
                shareWXMc.visible = false;
                resultMc.visible = true
            }
        };
        var wxCallbacks3 = {
            all: function (resp) {
                _hmt.push(["_trackPageview", "/games/christmas/share/weixin/sharetoweibo"]);
                shareWXMc.visible = false;
                resultMc.visible = true
            }
        };
        Api.shareToFriend(shareData, wxCallbacks1);
        Api.shareToTimeline(shareData2, wxCallbacks2);
        Api.shareToWeibo(shareData2, wxCallbacks3)
    });
    if (checkBrowserWeiXin()) {
        _hmt.push(["_trackPageview", "/games/christmas/terminal/" + LGlobal.os + "/is_weixin/" + $(window).width() + "x" + $(window).height()])
    } else {
        _hmt.push(["_trackPageview", "/games/christmas/terminal/" + LGlobal.os + "/no_weixin/" + $(window).width() + "x" + $(window).height()])
    }
});