function LAds(_funCallback, _LWidth) {
    base(this, LSprite, []);
    var _s = this;
    _s._inited = false;
    _s._autoPlay = false;
    _s._isPause = true;
    _s._runningId = null;
    _s._speed = 5000;
    _s._maxWidth = 600;
    _s._maxHeight = 120;
    _LWidth = (_LWidth || 400);
    _LWidth = Math.min(_s._maxWidth, _LWidth);
    _s._funCallback = (_funCallback || null);
    _s._realWidth = _LWidth;
    _s._realHeight = _LWidth * (_s._maxHeight / _s._maxWidth);
    var _LAdsList = [{
        "name": "_0",
        "path": "http:\/\/r3.ykimg.com\/05100000548EA6966737B37AED04266C",
        "type": "image"
    }, {
        "name": "_1",
        "path": "http:\/\/r3.ykimg.com\/05100000548FFFE26737B3506D044215",
        "type": "image"
    }, {"name": "_2", "path": "http:\/\/r1.ykimg.com\/051000005481521C6737B30B650CA898", "type": "image"}];
    _s.Assets = [{
        "name": "running",
        "url": "http:\/\/mp.weixin.qq.com\/s?__biz=MjM5NjAyMDk4MQ==&mid=201632761&idx=1&sn=84def17e208f93418f9dab95b64052d2#rd"
    }, {
        "name": "brain",
        "url": "http:\/\/mp.weixin.qq.com\/s?__biz=MjM5NjAyMDk4MQ==&mid=201528633&idx=1&sn=dc48d292175da0b25045d8c461d01802#rd"
    }, {"name": "chiq", "url": "http:\/\/minisite.youku.com\/chiq\/games\/index.html?from=christmas"}];
    _s._Len = _LAdsList.length;
    _s.Loading = new LoadingLAds(_s._realWidth, _s._realHeight);
    _s.addChild(_s.Loading);
    LLoadManage.load(_LAdsList, null, function (result) {
        _s.Resources = result;
        removeChild(_s.Loading);
        _s.init()
    })
}
LAds.prototype.init = function () {
    var _s = this;
    _s.Ads = new LSprite();
    _s.addChild(_s.Ads);
    var _mask = new LGraphics();
    _mask.drawRect(0, "#000000", [0, 0, _s._realWidth, _s._realHeight]);
    _s.Ads.mask = _mask;
    var _scale = _s._realWidth / _s._maxWidth;
    for (var i = 0; i < _s._Len; i++) {
        var child = new LAdsChild(new LBitmapData(_s.Resources['_' + i]), _scale);
        child.x = i * _s._realWidth;
        child.y = 0;
        child.ind = i;
        child.addEventListener(LMouseEvent.MOUSE_UP, function (e) {
            _s.AdsChildClick(e.currentTarget.ind)
        });
        _s.Ads.addChild(child)
    }
    _s._inited = true;
    if (_s._autoPlay) {
        _s.setPause(false)
    }
};
LAds.prototype.setPause = function (_b) {
    var _s = this;
    if (_s._inited) {
        if (_s._isPause === _b) {
            return
        }
        _s._isPause = _b;
        if (_s._runningId != null) {
            clearTimeout(_s._runningId);
            _s._runningId = null
        }
        if (!_b) {
            _s._Run()
        }
    } else {
        _s._autoPlay = !_b
    }
};
LAds.prototype._Run = function () {
    var _s = this;
    if (_s._isPause) {
        return
    }
    _s._isMoveing = false;
    if (_s._Len > 1) {
        setTimeout(function () {
            _s.AdsMove()
        }, _s._speed)
    }
};
LAds.prototype.AdsMove = function () {
    var _s = this;
    if (_s._isMoveing) {
        return
    }
    _s._isMoveing = true;
    var _gotoX = (_s.Ads.x <= -_s._realWidth * (_s._Len - 1)) ? 0 : _s.Ads.x - _s._realWidth;
    LTweenLite.to(_s.Ads, .5, {
        x: _gotoX, ease: Sine.easeInOut, onComplete: function (obj) {
            _s._Run()
        }
    })
};
LAds.prototype.AdsChildClick = function (_ind) {
    var _s = this;
    if (_s.Assets[_ind]) {
        if (typeof _s._funCallback === 'function') {
            _s._funCallback(_s.Assets[_ind])
        } else {
            location.href = _s.Assets[_ind]
        }
    }
};
function LoadingLAds(_LWidth, _LHeight) {
    base(this, LSprite, []);
    var _s = this;
    _s.graphics.drawRect(1, '#000000', [0, 0, _LWidth, _LHeight], true, '#000000');
    _s.arc = new LSprite();
    _s.arc.x = _LWidth * .5;
    _s.arc.y = _LHeight * .5;
    _s.addChild(_s.arc);
    var r = 12;
    for (var i = 0; i < 360; i += 45) {
        var child = new LSprite();
        child.graphics.drawArc(0, '#FFFFFF', [12, 0, 3, 0, 2 * Math.PI], true, '#FFFFFF');
        child.rotate = i;
        child.alpha = 0.1 + i / 360;
        _s.arc.addChild(child)
    }
    var shadow = new LDropShadowFilter(0, 0, "#FFFFFF", 30);
    _s.arc.filters = [shadow];
    _s.index = 0;
    _s.max = 2;
    _s.addEventListener(LEvent.ENTER_FRAME, _s.onframe)
}
LoadingLAds.prototype.onframe = function (event) {
    var _s = event.target;
    if (_s.index++ < _s.max) {
        return
    }
    _s.index = 0;
    _s.arc.rotate += 45
};
function LAdsChild(_bitmapData, _scale) {
    base(this, LSprite, []);
    _scale = (_scale || 1);
    var _child = new LBitmap(_bitmapData);
    _child.scaleX = _scale;
    _child.scaleY = _scale;
    this.addChild(_child)
}