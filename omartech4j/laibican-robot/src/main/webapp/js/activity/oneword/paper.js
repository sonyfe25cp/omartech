eval(function (p, a, c, k, e, r) {
    e = function (c) {
        return (c < 62 ? '' : e(parseInt(c / 62))) + ((c = c % 62) > 35 ? String.fromCharCode(c + 29) : c.toString(36))
    };
    if ('0'.replace(0, e) == 0) {
        while (c--)r[e(c)] = k[c];
        k = [function (e) {
            return r[e] || e
        }];
        e = function () {
            return '([9EG-OQ-Z]|[1-3]\\w)'
        };
        c = 1
    }
    ;
    while (c--)if (k[c])p = p.replace(new RegExp('\\b' + e(c) + '\\b', 'g'), k[c]);
    return p
}('(E(){G j="iVBORw0KGgoAAAANSUhEUgAAAGUAAAAnCAMAAAAhMnaaAAAC/VBMVEUAAADQyLPg2s24rIy3qom3qon8/Pr///+3qor///7////BtZm3qor///+2qYj///+3qon+/v38/Pvi3dDBtpr///7//v66rY63qoq9spT9/fy4q4u4rIz///////+4rIy4q4v+/v3+/v27r5HSyrbTzLj////Mw6z///63qoq3qoq4rIy2qYm4rIz////OxrDEuqC8sJK/tJe+s5a9spS5rI3///79/PvWz73t6uK5rY65rY7CuJzRybTNxa66ro+5rY3+/v24q4v////8/Pv9/fv8/Pr//v7////m4tbMxK3Lw6zq593HvaS6ro/Px7LFu6D5+PbSybX18u7y7+nq593v7OTSyrbGvKP08u3u7OS4rIy3q4q4q4u3qom2qYn9/fz////Y0b/r596/tJj////////////e2MnDuJ3b1cTx7ufWz73HvaXBt5y8sJLq5ty6ro/Wz73Mw6z////EuZ/Jv6b59/S4rIz39fG/s5bPx7L+/v3X0L7Qx7L+/v25rY7////GvaP9/Pv08+7PxrH+/v3////r6N////////+2qYjHvqXJwKfHvqX9/Pvl4NXu6+P////h3M7Z08Ld18jY0sHJwKfh3M/Fu6Hf2cr+/v3l4db18+/5+PX////c1cXn49jBtpvHvaTLwqvY0sD+/v7s6eC9spX39fHx7+nt6uL///+6ro/////Kwam7r5D////Y0sD39fG+s5W5rY76+fbZ0sH////r59/FuqD08u3IvqX9/fzUzLjf2svi3tHZ0sHg283DuZ3g283y7+nu7OTt6uL////i3dDMw6zJwKjKwKnl4dX29PC+s5bg2szPx7Hk4NTWzrzHvaO6ro/Px7He2crSyrbc1sfNxK3////j3tH6+fbr6N/f2cvUzbrTy7jSy7fPx7LMxK3UzbnHvqXDuJ3QyLPKwqr49/TVzrzRybXOxq/Y0sHJwKjb1MS/s5f7+vjGvKLBtprd18fo5Nrf2szEup/w7ebj39LX0L7z8uzx7+js6eBUyNMAAAAA4HRSTlMAAQMLBhMIuj0lBPcZqR/MOBwW/OfAoFkj/OyrmHtPQy0Z/v338O3r5HtrZmEoDvz35t7XyMUsIPnt3dXSysm8sYl1c1M5KRQL/vz79vTz7Ozo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";E d(a){T I.1O(a)}E m(){E a(){"2r"==1p L.1q&&0!==L.1q||"E"!=1p m||12.reload()}L.onorientationchange=a;H("2r"==1p L.1q&&0!==L.1q)13("\\1r\\u65cb\\u8f6c\\u60a8\\1h\\u624b\\u673a\\2s\\u7ad6\\u7acb\\u72b6\\u6001\\U\\2t\\u6a2a\\u5c4f\\u754c\\u9762\\1P\\u65e0\\1Q\\u64cd\\u4f5c\\X"),a();S{m=1s;G c=I.1R.clientWidth,b=c/640;I.1R.J.fontSize=16*b+"px";I.1R.J.1t=c+"px";G n=d("O"),e=d("paperLogo");n.1t=Y*b;n.1S=Y*b;G f=1u r(n);f.b=c;I.1O("clearCanvas").1i=E(){f.A();p=0;k=""};e.1v="1T:1w/15;2v,"+j;I.ontouchmove=E(b){b.1U()};G l=0,h=d("palette"),q=d("penColor");d("btnPenColor").1i=E(){l?(l=0,h.J.Q="1x"):(l=1,h.J.Q="V")};h.1i=E(b){b=b.target;H("LI"==b.tagName){G a=b.getAttribute("value");f.17=a;q.J.2w=b.J.2w;l=0;h.J.Q="1x"}};d("shareBtn").1i=E(){H(0==f.k)13("\\u522b\\u9017\\2x\\U\\1V\\1W\\1X\\u4ec0\\1X\\u90fd\\u6ca1\\u770b\\u5230\\u54ea\\X");S H(20>f.k)13("\\u4e0d\\u8981\\1y\\1X\\u4efb\\u6027\\U\\1y\\1Y\\u592a\\u7b80\\u6d01\\2x\\X");S H(p==f.k&&""!=k)g("1Z");S{p=f.k;g("Z","<p>\\2y\\2z\\1j\\1k\\1l...</p>");G b,a=f.B("jpeg",.6);"1T:1w/15"==a.18(0,14)?(a=a.18(22),b="15"):(a=a.18(23),b="1a");"15"!=b||t||!s||confirm("\\2y\\2z\\1h\\1j\\1k\\2s"+M.1b(a.R/1z)+"k\\U\\u662f\\u5426\\1c\\1d\\21")?(g("Z","<p>\\1c\\1d\\1j\\1k\\1l(\\2A"+M.1b(a.R/1z)+")</p>"),u(a,b)):g("11")}};d("toEditorBtn").1i=E(){g("11")};(c=12.1A.2B(/\\/wx\\/1C\\/(\\d+)/))?(g("2C","1e://i0.26.W/27/28/"+c[1].18(0,25==c[1].R?8:10)+"/"+c[1]+".1a"),L.1D&&1D.2D==c[1]&&(d("saveMemo").J.Q="V",2E("\\1f\\2F\\1E\\2G\\2H\\1y\\1f\\2I\\U\\1V\\1E\\2J\\2K\\1W\\2L\\21\\1Y\\2M\\2N\\1f\\1P\\2O\\1h\\2P\\1Q\\2Q\\X","1e://t.1m.1F.W/wx/1C/"+c[1],"1e://i0.26.W/27/28/"+c[1].18(0,25==c[1].R?8:10)+"/s_"+c[1]+".1a"))):g("11")}}E u(a,c){G b=1u XMLHttpRequest;c=c||"1a";b.onreadystatechange=E(){H(4==b.2S&&2T==b.29){G a=b.responseText;a&&/^\\d+$/.test(a)?(k="1e://i0.26.W/27/28/"+a.18(0,25==a.R?8:10)+"/s_"+a+".1a",2E("\\1f\\2F\\1E\\2G\\2H\\1y\\1f\\2I\\U\\1V\\1E\\2J\\2K\\1W\\2L\\21\\1Y\\2M\\2N\\1f\\1P\\2O\\1h\\2P\\1Q\\2Q\\X","1e://t.1m.1F.W/wx/1C/"+a,k),g("1Z"),L.1D&&(1D.2D=a)):(13("\\1c\\1d\\u5931\\u8d25\\U\\1r\\2U\\2V\\2a\\2b\\X"),g("11"))}S 4==b.2S&&2T!==b.29&&(13("\\2W\\2X\\u5fd9\\U\\1r\\2U\\2V\\2a\\2b\\uff01code:"+b.29),g("11"))};b.ontimeout=E(){13("\\1c\\1d\\u8d85\\u65f6\\U\\1r\\2t\\u8f83\\u597d\\1h\\2W\\2X\\u73af\\u5883\\1l\\2a\\2b\\X");g("11")};b.Z&&(b.Z.onprogress=E(a){a.lengthComputable&&g("Z","<p>\\1j\\1k\\1c\\1d\\1l<2Y>(\\2A"+M.1b(a.2Z/1z)+"k/\\u8fdb\\u5ea6"+M.1b(a.loaded/a.2Z*100)+"%)</2Y></p>")});b.open("POST","/weixinImageUpload.action",!0);b.setRequestHeader("Content-Type","application/x-www-form-urlencoded");a="dataStr="+encodeURIComponent(a)+"&1G="+c;g("Z","<p>\\1j\\1k\\1c\\1d\\1l("+M.1b(a.R/1z)+"k)...</p>");b.send(a)}E g(a,c){"Z"==a&&""!=c?(h(),d("1H").J.Q="V",d("2c").J.Q="V",d("2c").innerHTML=c):"11"==a?(h(),d("1H").J.Q="V"):"2C"==a&&""!=c?(h(),d("30").J.Q="V",d("showImg").1v=c):"1Z"==a&&(h(),d("1H").J.Q="V",d("31").J.Q="V",setTimeout(E(){d("31").J.Q="1x"},15E3))}E h(){d("1H").J.Q=d("2c").J.Q=d("30").J.Q="1x"}E r(a){H(a.nodeType)9.O=a;S H("string"==1p a)9.O=I.1O(a);S T;9.C()}G q=2e.2f.2B(/ OS (\\d+).*? Mac OS/)||!1,k="",p=0,t=-1!==2e.2f.33("NetType/WIFI"),s=-1!==2e.2f.33("Messenger");H(s){H(L.frameElement){L.1n.12.1A=12.1A;T}H("t.1m.1F.W"!=12.host){12.1A="1e://t.1m.1F.W/wx/1C";T}}L.2g&&L.2g.34&&2g.34("Copyright by 1m.W");r.prototype={K:1,17:"35(0,0,0, .6)",c:8,b:N,v:"/microblog-v3/2014subject/1216_oneword/images/paper.1a",k:0,C:E(){G a=9;H(9.O.2h){9.a=9.O.2h("2d");9.a.2i=9.17;9.a.36=9.17;9.h(9.O,"selectstart",E(){T!1});9.o=1u 37;9.o.1v=9.v;9.p=1u 37;9.p.1v="1T:1w/15;2v,"+j;G c=E(b){G d,e;H("2j"==b.1G){H(2<=b.1g.R)T;d=b.1g[0].1I;e=b.1g[0].1J;a.f(a.O,"38",c)}S d=b.1I,e=b.1J;a.D(d,e,b.1G);b.1U()};9.h(9.O,"2j",c);9.h(9.O,"38",c)}},D:E(a,c,b){G d=9;9.i=9.O.getBoundingClientRect();9.i={1K:9.i.1K+L.scrollX,1n:9.i.1n+L.scrollY};L.3a()?L.3a().removeAllRanges():I.selection.empty();9.a.3b(a-9.i.1K,c-9.i.1n);9.e=1s;9.s=0;9.d=[];9.K=9.c/2*(9.b/N);9.g&&(9.f(I,"2k",9.g),9.f(I,"1L",9.g),9.f(I,"2l",9.j),9.f(I,"2m",9.j));9.g=E(a){G b,c;H("1L"==a.1G){H(2<=a.1g.R)T;b=a.1g[0].1I;c=a.1g[0].1J}S b=a.1I,c=a.1J;d.t(b,c);a.1U()};9.j=E(){d.F()};"2j"==b?(9.h(I,"1L",9.g),9.h(I,"2m",9.j)):(9.h(I,"2k",9.g),9.h(I,"2l",9.j));9.w();9.t(a,c)},t:E(a,c){G b;b=0;a-=9.i.1K;c-=9.i.1n;H(9.d.R&&(b=9.d[9.d.R-1],b=M.3c((b.x-a)*(b.x-a)+(b.y-c)*(b.y-c)),0==b))T;9.k++;9.d.push({x:a,y:c,r:b});3<=9.d.R&&(b=9.d.3d(),9.q(b))},q:E(a,c){G b=a.x,d=a.y,e=a.r,f=q?4:2;H(!9.e||0!==e){G g=9.d.R?9.d[0]:1s;H(e){9.a.3b(9.e.x,9.e.y);H(!9.s&&(9.s=1,g&&e>g.r*f))2n(e/=4,f=1;4>=f;f++)9.u(b+f/4*(9.e.x-b),d+f/4*(9.e.y-d));c||(c=e<.003125*9.b?9.b/N*9.c*1.3e:e<.00625*9.b?9.b/N*9.c*1.3f:e<.009375*9.b?9.b/N*9.c*1.25:e<.015625*9.b?9.b/N*9.c*1.125:e<.021875*9.b?9.b/N*9.c:e<.028125*9.b?9.b/N*9.c*.875:e<.034375*9.b?9.b/N*9.c*.75:e<.046875*9.b?9.b/N*9.c*.3e:e<.0625*9.b?9.b/N*9.c*.5:9.b/N*9.c*.3f);9.n=c;M.1M(9.K-9.n)>9.b/N*9.c*.3g&&(9.K-=(9.K-9.n)/8,9.a.K=9.K)}9.e=a;9.u(b,d)}},F:E(){9.f(I,"2k",9.g);9.f(I,"1L",9.g);9.f(I,"2l",9.j);9.f(I,"2m",9.j);--9.a.K;2n(G a;9.d.R;)a=9.d.3d(),9.q(a,9.b/N*9.c/4)},A:E(){9.e=1s;9.k=0;9.a.clearRect(0,0,9.O.1t,9.O.1S);9.a.1o()},u:E(a,c){G b,d,e;9.a.36=9.17;9.a.2i=9.17;9.a.lineTo(9.e.x,9.e.y);9.a.2o();9.a.1o();9.a.1o();9.a.2i="35(0, 0, 0, 0)";H(9.l||9.m)H(b=9.l-a,d=9.m-c,e=M.3c((9.l-a)*(9.l-a)+(9.m-c)*(9.m-c)),M.1M(e)>9.K/3){e=M.floor(M.1M(e)/(9.K/3));2n(G f=1;f<=e;f++)M.1M(9.K-9.n)>9.b/N*9.c*.3g&&(9.K-=M.1b(9.K-9.n)/8,9.a.K=9.K),9.a.3h(9.l-f/e*b,9.m-f/e*d,9.K,0,2*M.PI),9.a.3j(),9.a.2o(),9.a.1o()}9.a.3h(a,c,9.K,0,2*M.PI);9.a.3j();9.a.2o();9.a.1o();9.l=a;9.m=c},w:E(){9.m=9.l=0},h:E(a,c,b){a.3k?a.3k("on"+c,b):a.addEventListener(c,b,!1)},f:E(a,c,b){a.3m?a.3m("on"+c,b):a.removeEventListener(c,b,!1)},B:E(a,c){G b;G d;H(9.p.3n&&9.o.3n){b=a||"15";d=c||.7;G e=I.createElement("O");e.1t=e.1S=Y;G f=e.2h("2d");f.2p(9.o,0,0,Y,Y);f.2p(9.O,0,0,Y,Y);f.2p(9.p,19,16,101,39);b=e.toDataURL("1w/"+b,d)}S b="";T b}};m()})();', [], 210, '|||||||||this|||||||||||||||||||||||||||||||function||var|if|document|style|lineWidth|window|Math|320|canvas||display|length|else|return|uff0c|block|cn|uff01|590|upload||editor|location|alert||png||color|substr||jpg|round|u4e0a|u4f20|http|u4e00|touches|u7684|onclick|u56fe|u7247|u4e2d|people|top|beginPath|typeof|orientation|u8bf7|null|width|new|src|image|none|u8fd9|1024|href||oneword|localStorage|u5b57|com|type|editorPage|pageX|pageY|left|touchmove|abs||getElementById|u4e0b|u6cd5|body|height|data|preventDefault|u6211|u600e|u4e48|u4e5f|share||uff1f|||||peopleurl|msgimage|weixin|status|u518d|u8bd5|uploadPage||navigator|userAgent|console|getContext|strokeStyle|touchstart|mousemove|mouseup|touchend|for|stroke|drawImage||number|u4e3a|u5728||base64|background|u4e86|u751f|u6210|u5171|match|show|onewordId|setShareData|u4e2a|u5f62|u5bb9|u5e74|u5199|u5f97|u6837|u6765|u6652|u4f60|u4e66|u5427||readyState|200|u7a0d|u540e|u7f51|u7edc|span|total|showPage|shareAlert||indexOf|log|rgba|fillStyle|Image|mousedown||getSelection|moveTo|sqrt|shift|625|375|025|arc||fill|attachEvent||detachEvent|complete'.split('|'), 0, {}))