/* Zepto 1.1.3 - zepto event detect touch ajax callbacks deferred - zeptojs.com/license */

var Zepto = function () {
    function M(e) {
        return e == null ? String(e) : x[T.call(e)] || "object"
    }

    function _(e) {
        return M(e) == "function"
    }

    function D(e) {
        return e != null && e == e.window
    }

    function P(e) {
        return e != null && e.nodeType == e.DOCUMENT_NODE
    }

    function H(e) {
        return M(e) == "object"
    }

    function B(e) {
        return H(e) && !D(e) && Object.getPrototypeOf(e) == Object.prototype
    }

    function j(e) {
        return typeof e.length == "number"
    }

    function F(e) {
        return o.call(e, function (e) {
            return e != null
        })
    }

    function I(e) {
        return e.length > 0 ? n.fn.concat.apply([], e) : e
    }

    function q(e) {
        return e.replace(/::/g, "/").replace(/([A-Z]+)([A-Z][a-z])/g, "$1_$2").replace(/([a-z\d])([A-Z])/g, "$1_$2").replace(/_/g, "-").toLowerCase()
    }

    function R(e) {
        return e in f ? f[e] : f[e] = new RegExp("(^|\\s)" + e + "(\\s|$)")
    }

    function U(e, t) {
        return typeof t == "number" && !l[q(e)] ? t + "px" : t
    }

    function z(e) {
        var t, n;
        return a[e] || (t = u.createElement(e), u.body.appendChild(t), n = getComputedStyle(t, "").getPropertyValue("display"), t.parentNode.removeChild(t), n == "none" && (n = "block"), a[e] = n), a[e]
    }

    function W(e) {
        return "children"in e ? s.call(e.children) : n.map(e.childNodes, function (e) {
            if (e.nodeType == 1)return e
        })
    }

    function X(n, r, i) {
        for (t in r)i && (B(r[t]) || O(r[t])) ? (B(r[t]) && !B(n[t]) && (n[t] = {}), O(r[t]) && !O(n[t]) && (n[t] = []), X(n[t], r[t], i)) : r[t] !== e && (n[t] = r[t])
    }

    function V(e, t) {
        return t == null ? n(e) : n(e).filter(t)
    }

    function $(e, t, n, r) {
        return _(t) ? t.call(e, n, r) : t
    }

    function J(e, t, n) {
        n == null ? e.removeAttribute(t) : e.setAttribute(t, n)
    }

    function K(t, n) {
        var r = t.className, i = r && r.baseVal !== e;
        if (n === e)return i ? r.baseVal : r;
        i ? r.baseVal = n : t.className = n
    }

    function Q(e) {
        var t;
        try {
            return e ? e == "true" || (e == "false" ? !1 : e == "null" ? null : !/^0/.test(e) && !isNaN(t = Number(e)) ? t : /^[\[\{]/.test(e) ? n.parseJSON(e) : e) : e
        } catch (r) {
            return e
        }
    }

    function G(e, t) {
        t(e);
        for (var n in e.childNodes)G(e.childNodes[n], t)
    }

    var e, t, n, r, i = [], s = i.slice, o = i.filter, u = window.document, a = {}, f = {}, l = {
        "column-count": 1,
        columns: 1,
        "font-weight": 1,
        "line-height": 1,
        opacity: 1,
        "z-index": 1,
        zoom: 1
    }, c = /^\s*<(\w+|!)[^>]*>/, h = /^<(\w+)\s*\/?>(?:<\/\1>|)$/, p = /<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\w:]+)[^>]*)\/>/ig, d = /^(?:body|html)$/i, v = /([A-Z])/g, m = ["val", "css", "html", "text", "data", "width", "height", "offset"], g = ["after", "prepend", "before", "append"], y = u.createElement("table"), b = u.createElement("tr"), w = {
        tr: u.createElement("tbody"),
        tbody: y,
        thead: y,
        tfoot: y,
        td: b,
        th: b,
        "*": u.createElement("div")
    }, E = /complete|loaded|interactive/, S = /^[\w-]*$/, x = {}, T = x.toString, N = {}, C, k, L = u.createElement("div"), A = {
        tabindex: "tabIndex",
        readonly: "readOnly",
        "for": "htmlFor",
        "class": "className",
        maxlength: "maxLength",
        cellspacing: "cellSpacing",
        cellpadding: "cellPadding",
        rowspan: "rowSpan",
        colspan: "colSpan",
        usemap: "useMap",
        frameborder: "frameBorder",
        contenteditable: "contentEditable"
    }, O = Array.isArray || function (e) {
            return e instanceof Array
        };
    return N.matches = function (e, t) {
        if (!t || !e || e.nodeType !== 1)return !1;
        var n = e.webkitMatchesSelector || e.mozMatchesSelector || e.oMatchesSelector || e.matchesSelector;
        if (n)return n.call(e, t);
        var r, i = e.parentNode, s = !i;
        return s && (i = L).appendChild(e), r = ~N.qsa(i, t).indexOf(e), s && L.removeChild(e), r
    }, C = function (e) {
        return e.replace(/-+(.)?/g, function (e, t) {
            return t ? t.toUpperCase() : ""
        })
    }, k = function (e) {
        return o.call(e, function (t, n) {
            return e.indexOf(t) == n
        })
    }, N.fragment = function (t, r, i) {
        var o, a, f;
        return h.test(t) && (o = n(u.createElement(RegExp.$1))), o || (t.replace && (t = t.replace(p, "<$1></$2>")), r === e && (r = c.test(t) && RegExp.$1), r in w || (r = "*"), f = w[r], f.innerHTML = "" + t, o = n.each(s.call(f.childNodes), function () {
            f.removeChild(this)
        })), B(i) && (a = n(o), n.each(i, function (e, t) {
            m.indexOf(e) > -1 ? a[e](t) : a.attr(e, t)
        })), o
    }, N.Z = function (e, t) {
        return e = e || [], e.__proto__ = n.fn, e.selector = t || "", e
    }, N.isZ = function (e) {
        return e instanceof N.Z
    }, N.init = function (t, r) {
        var i;
        if (!t)return N.Z();
        if (typeof t == "string") {
            t = t.trim();
            if (t[0] == "<" && c.test(t))i = N.fragment(t, RegExp.$1, r), t = null; else {
                if (r !== e)return n(r).find(t);
                i = N.qsa(u, t)
            }
        } else {
            if (_(t))return n(u).ready(t);
            if (N.isZ(t))return t;
            if (O(t))i = F(t); else if (H(t))i = [t], t = null; else if (c.test(t))i = N.fragment(t.trim(), RegExp.$1, r), t = null; else {
                if (r !== e)return n(r).find(t);
                i = N.qsa(u, t)
            }
        }
        return N.Z(i, t)
    }, n = function (e, t) {
        return N.init(e, t)
    }, n.extend = function (e) {
        var t, n = s.call(arguments, 1);
        return typeof e == "boolean" && (t = e, e = n.shift()), n.forEach(function (n) {
            X(e, n, t)
        }), e
    }, N.qsa = function (e, t) {
        var n, r = t[0] == "#", i = !r && t[0] == ".", o = r || i ? t.slice(1) : t, u = S.test(o);
        return P(e) && u && r ? (n = e.getElementById(o)) ? [n] : [] : e.nodeType !== 1 && e.nodeType !== 9 ? [] : s.call(u && !r ? i ? e.getElementsByClassName(o) : e.getElementsByTagName(t) : e.querySelectorAll(t))
    }, n.contains = function (e, t) {
        return e !== t && e.contains(t)
    }, n.type = M, n.isFunction = _, n.isWindow = D, n.isArray = O, n.isPlainObject = B, n.isEmptyObject = function (e) {
        var t;
        for (t in e)return !1;
        return !0
    }, n.inArray = function (e, t, n) {
        return i.indexOf.call(t, e, n)
    }, n.camelCase = C, n.trim = function (e) {
        return e == null ? "" : String.prototype.trim.call(e)
    }, n.uuid = 0, n.support = {}, n.expr = {}, n.map = function (e, t) {
        var n, r = [], i, s;
        if (j(e))for (i = 0; i < e.length; i++)n = t(e[i], i), n != null && r.push(n); else for (s in e)n = t(e[s], s), n != null && r.push(n);
        return I(r)
    }, n.each = function (e, t) {
        var n, r;
        if (j(e)) {
            for (n = 0; n < e.length; n++)if (t.call(e[n], n, e[n]) === !1)return e
        } else for (r in e)if (t.call(e[r], r, e[r]) === !1)return e;
        return e
    }, n.grep = function (e, t) {
        return o.call(e, t)
    }, window.JSON && (n.parseJSON = JSON.parse), n.each("Boolean Number String Function Array Date RegExp Object Error".split(" "), function (e, t) {
        x["[object " + t + "]"] = t.toLowerCase()
    }), n.fn = {
        forEach: i.forEach,
        reduce: i.reduce,
        push: i.push,
        sort: i.sort,
        indexOf: i.indexOf,
        concat: i.concat,
        map: function (e) {
            return n(n.map(this, function (t, n) {
                return e.call(t, n, t)
            }))
        },
        slice: function () {
            return n(s.apply(this, arguments))
        },
        ready: function (e) {
            return E.test(u.readyState) && u.body ? e(n) : u.addEventListener("DOMContentLoaded", function () {
                e(n)
            }, !1), this
        },
        get: function (t) {
            return t === e ? s.call(this) : this[t >= 0 ? t : t + this.length]
        },
        toArray: function () {
            return this.get()
        },
        size: function () {
            return this.length
        },
        remove: function () {
            return this.each(function () {
                this.parentNode != null && this.parentNode.removeChild(this)
            })
        },
        each: function (e) {
            return i.every.call(this, function (t, n) {
                return e.call(t, n, t) !== !1
            }), this
        },
        filter: function (e) {
            return _(e) ? this.not(this.not(e)) : n(o.call(this, function (t) {
                return N.matches(t, e)
            }))
        },
        add: function (e, t) {
            return n(k(this.concat(n(e, t))))
        },
        is: function (e) {
            return this.length > 0 && N.matches(this[0], e)
        },
        not: function (t) {
            var r = [];
            if (_(t) && t.call !== e)this.each(function (e) {
                t.call(this, e) || r.push(this)
            }); else {
                var i = typeof t == "string" ? this.filter(t) : j(t) && _(t.item) ? s.call(t) : n(t);
                this.forEach(function (e) {
                    i.indexOf(e) < 0 && r.push(e)
                })
            }
            return n(r)
        },
        has: function (e) {
            return this.filter(function () {
                return H(e) ? n.contains(this, e) : n(this).find(e).size()
            })
        },
        eq: function (e) {
            return e === -1 ? this.slice(e) : this.slice(e, +e + 1)
        },
        first: function () {
            var e = this[0];
            return e && !H(e) ? e : n(e)
        },
        last: function () {
            var e = this[this.length - 1];
            return e && !H(e) ? e : n(e)
        },
        find: function (e) {
            var t, r = this;
            return typeof e == "object" ? t = n(e).filter(function () {
                var e = this;
                return i.some.call(r, function (t) {
                    return n.contains(t, e)
                })
            }) : this.length == 1 ? t = n(N.qsa(this[0], e)) : t = this.map(function () {
                return N.qsa(this, e)
            }), t
        },
        closest: function (e, t) {
            var r = this[0], i = !1;
            typeof e == "object" && (i = n(e));
            while (r && !(i ? i.indexOf(r) >= 0 : N.matches(r, e)))r = r !== t && !P(r) && r.parentNode;
            return n(r)
        },
        parents: function (e) {
            var t = [], r = this;
            while (r.length > 0)r = n.map(r, function (e) {
                if ((e = e.parentNode) && !P(e) && t.indexOf(e) < 0)return t.push(e), e
            });
            return V(t, e)
        },
        parent: function (e) {
            return V(k(this.pluck("parentNode")), e)
        },
        children: function (e) {
            return V(this.map(function () {
                return W(this)
            }), e)
        },
        contents: function () {
            return this.map(function () {
                return s.call(this.childNodes)
            })
        },
        siblings: function (e) {
            return V(this.map(function (e, t) {
                return o.call(W(t.parentNode), function (e) {
                    return e !== t
                })
            }), e)
        },
        empty: function () {
            return this.each(function () {
                this.innerHTML = ""
            })
        },
        pluck: function (e) {
            return n.map(this, function (t) {
                return t[e]
            })
        },
        show: function () {
            return this.each(function () {
                this.style.display == "none" && (this.style.display = ""), getComputedStyle(this, "").getPropertyValue("display") == "none" && (this.style.display = z(this.nodeName))
            })
        },
        replaceWith: function (e) {
            return this.before(e).remove()
        },
        wrap: function (e) {
            var t = _(e);
            if (this[0] && !t)var r = n(e).get(0), i = r.parentNode || this.length > 1;
            return this.each(function (s) {
                n(this).wrapAll(t ? e.call(this, s) : i ? r.cloneNode(!0) : r)
            })
        },
        wrapAll: function (e) {
            if (this[0]) {
                n(this[0]).before(e = n(e));
                var t;
                while ((t = e.children()).length)e = t.first();
                n(e).append(this)
            }
            return this
        },
        wrapInner: function (e) {
            var t = _(e);
            return this.each(function (r) {
                var i = n(this), s = i.contents(), o = t ? e.call(this, r) : e;
                s.length ? s.wrapAll(o) : i.append(o)
            })
        },
        unwrap: function () {
            return this.parent().each(function () {
                n(this).replaceWith(n(this).children())
            }), this
        },
        clone: function () {
            return this.map(function () {
                return this.cloneNode(!0)
            })
        },
        hide: function () {
            return this.css("display", "none")
        },
        toggle: function (t) {
            return this.each(function () {
                var r = n(this);
                (t === e ? r.css("display") == "none" : t) ? r.show() : r.hide()
            })
        },
        prev: function (e) {
            return n(this.pluck("previousElementSibling")).filter(e || "*")
        },
        next: function (e) {
            return n(this.pluck("nextElementSibling")).filter(e || "*")
        },
        html: function (e) {
            return arguments.length === 0 ? this.length > 0 ? this[0].innerHTML : null : this.each(function (t) {
                var r = this.innerHTML;
                n(this).empty().append($(this, e, t, r))
            })
        },
        text: function (t) {
            return arguments.length === 0 ? this.length > 0 ? this[0].textContent : null : this.each(function () {
                this.textContent = t === e ? "" : "" + t
            })
        },
        attr: function (n, r) {
            var i;
            return typeof n == "string" && r === e ? this.length == 0 || this[0].nodeType !== 1 ? e : n == "value" && this[0].nodeName == "INPUT" ? this.val() : !(i = this[0].getAttribute(n)) && n in this[0] ? this[0][n] : i : this.each(function (e) {
                if (this.nodeType !== 1)return;
                if (H(n))for (t in n)J(this, t, n[t]); else J(this, n, $(this, r, e, this.getAttribute(n)))
            })
        },
        removeAttr: function (e) {
            return this.each(function () {
                this.nodeType === 1 && J(this, e)
            })
        },
        prop: function (t, n) {
            return t = A[t] || t, n === e ? this[0] && this[0][t] : this.each(function (e) {
                this[t] = $(this, n, e, this[t])
            })
        },
        data: function (t, n) {
            var r = this.attr("data-" + t.replace(v, "-$1").toLowerCase(), n);
            return r !== null ? Q(r) : e
        },
        val: function (e) {
            return arguments.length === 0 ? this[0] && (this[0].multiple ? n(this[0]).find("option").filter(function () {
                return this.selected
            }).pluck("value") : this[0].value) : this.each(function (t) {
                this.value = $(this, e, t, this.value)
            })
        },
        offset: function (e) {
            if (e)return this.each(function (t) {
                var r = n(this), i = $(this, e, t, r.offset()), s = r.offsetParent().offset(), o = {
                    top: i.top - s.top,
                    left: i.left - s.left
                };
                r.css("position") == "static" && (o.position = "relative"), r.css(o)
            });
            if (this.length == 0)return null;
            var t = this[0].getBoundingClientRect();
            return {
                left: t.left + window.pageXOffset,
                top: t.top + window.pageYOffset,
                width: Math.round(t.width),
                height: Math.round(t.height)
            }
        },
        css: function (e, r) {
            if (arguments.length < 2) {
                var i = this[0], s = getComputedStyle(i, "");
                if (!i)return;
                if (typeof e == "string")return i.style[C(e)] || s.getPropertyValue(e);
                if (O(e)) {
                    var o = {};
                    return n.each(O(e) ? e : [e], function (e, t) {
                        o[t] = i.style[C(t)] || s.getPropertyValue(t)
                    }), o
                }
            }
            var u = "";
            if (M(e) == "string")!r && r !== 0 ? this.each(function () {
                this.style.removeProperty(q(e))
            }) : u = q(e) + ":" + U(e, r); else for (t in e)!e[t] && e[t] !== 0 ? this.each(function () {
                this.style.removeProperty(q(t))
            }) : u += q(t) + ":" + U(t, e[t]) + ";";
            return this.each(function () {
                this.style.cssText += ";" + u
            })
        },
        index: function (e) {
            return e ? this.indexOf(n(e)[0]) : this.parent().children().indexOf(this[0])
        },
        hasClass: function (e) {
            return e ? i.some.call(this, function (e) {
                return this.test(K(e))
            }, R(e)) : !1
        },
        addClass: function (e) {
            return e ? this.each(function (t) {
                r = [];
                var i = K(this), s = $(this, e, t, i);
                s.split(/\s+/g).forEach(function (e) {
                    n(this).hasClass(e) || r.push(e)
                }, this), r.length && K(this, i + (i ? " " : "") + r.join(" "))
            }) : this
        },
        removeClass: function (t) {
            return this.each(function (n) {
                if (t === e)return K(this, "");
                r = K(this), $(this, t, n, r).split(/\s+/g).forEach(function (e) {
                    r = r.replace(R(e), " ")
                }), K(this, r.trim())
            })
        },
        toggleClass: function (t, r) {
            return t ? this.each(function (i) {
                var s = n(this), o = $(this, t, i, K(this));
                o.split(/\s+/g).forEach(function (t) {
                    (r === e ? !s.hasClass(t) : r) ? s.addClass(t) : s.removeClass(t)
                })
            }) : this
        },
        scrollTop: function (t) {
            if (!this.length)return;
            var n = "scrollTop"in this[0];
            return t === e ? n ? this[0].scrollTop : this[0].pageYOffset : this.each(n ? function () {
                this.scrollTop = t
            } : function () {
                this.scrollTo(this.scrollX, t)
            })
        },
        scrollLeft: function (t) {
            if (!this.length)return;
            var n = "scrollLeft"in this[0];
            return t === e ? n ? this[0].scrollLeft : this[0].pageXOffset : this.each(n ? function () {
                this.scrollLeft = t
            } : function () {
                this.scrollTo(t, this.scrollY)
            })
        },
        position: function () {
            if (!this.length)return;
            var e = this[0], t = this.offsetParent(), r = this.offset(), i = d.test(t[0].nodeName) ? {
                top: 0,
                left: 0
            } : t.offset();
            return r.top -= parseFloat(n(e).css("margin-top")) || 0, r.left -= parseFloat(n(e).css("margin-left")) || 0, i.top += parseFloat(n(t[0]).css("border-top-width")) || 0, i.left += parseFloat(n(t[0]).css("border-left-width")) || 0, {
                top: r.top - i.top,
                left: r.left - i.left
            }
        },
        offsetParent: function () {
            return this.map(function () {
                var e = this.offsetParent || u.body;
                while (e && !d.test(e.nodeName) && n(e).css("position") == "static")e = e.offsetParent;
                return e
            })
        }
    }, n.fn.detach = n.fn.remove, ["width", "height"].forEach(function (t) {
        var r = t.replace(/./, function (e) {
            return e[0].toUpperCase()
        });
        n.fn[t] = function (i) {
            var s, o = this[0];
            return i === e ? D(o) ? o["inner" + r] : P(o) ? o.documentElement["scroll" + r] : (s = this.offset()) && s[t] : this.each(function (e) {
                o = n(this), o.css(t, $(this, i, e, o[t]()))
            })
        }
    }), g.forEach(function (e, t) {
        var r = t % 2;
        n.fn[e] = function () {
            var e, i = n.map(arguments, function (t) {
                return e = M(t), e == "object" || e == "array" || t == null ? t : N.fragment(t)
            }), s, o = this.length > 1;
            return i.length < 1 ? this : this.each(function (e, u) {
                s = r ? u : u.parentNode, u = t == 0 ? u.nextSibling : t == 1 ? u.firstChild : t == 2 ? u : null, i.forEach(function (e) {
                    if (o)e = e.cloneNode(!0); else if (!s)return n(e).remove();
                    G(s.insertBefore(e, u), function (e) {
                        e.nodeName != null && e.nodeName.toUpperCase() === "SCRIPT" && (!e.type || e.type === "text/javascript") && !e.src && window.eval.call(window, e.innerHTML)
                    })
                })
            })
        }, n.fn[r ? e + "To" : "insert" + (t ? "Before" : "After")] = function (t) {
            return n(t)[e](this), this
        }
    }), N.Z.prototype = n.fn, N.uniq = k, N.deserializeValue = Q, n.zepto = N, n
}();
window.Zepto = Zepto, window.$ === undefined && (window.$ = Zepto), function (e) {
    function c(e) {
        return e._zid || (e._zid = t++)
    }

    function h(e, t, n, r) {
        t = p(t);
        if (t.ns)var i = d(t.ns);
        return (o[c(e)] || []).filter(function (e) {
            return e && (!t.e || e.e == t.e) && (!t.ns || i.test(e.ns)) && (!n || c(e.fn) === c(n)) && (!r || e.sel == r)
        })
    }

    function p(e) {
        var t = ("" + e).split(".");
        return {e: t[0], ns: t.slice(1).sort().join(" ")}
    }

    function d(e) {
        return new RegExp("(?:^| )" + e.replace(" ", " .* ?") + "(?: |$)")
    }

    function v(e, t) {
        return e.del && !a && e.e in f || !!t
    }

    function m(e) {
        return l[e] || a && f[e] || e
    }

    function g(t, r, i, s, u, a, f) {
        var h = c(t), d = o[h] || (o[h] = []);
        r.split(/\s/).forEach(function (r) {
            if (r == "ready")return e(document).ready(i);
            var o = p(r);
            o.fn = i, o.sel = u, o.e in l && (i = function (t) {
                var n = t.relatedTarget;
                if (!n || n !== this && !e.contains(this, n))return o.fn.apply(this, arguments)
            }), o.del = a;
            var c = a || i;
            o.proxy = function (e) {
                e = x(e);
                if (e.isImmediatePropagationStopped())return;
                e.data = s;
                var r = c.apply(t, e._args == n ? [e] : [e].concat(e._args));
                return r === !1 && (e.preventDefault(), e.stopPropagation()), r
            }, o.i = d.length, d.push(o), "addEventListener"in t && t.addEventListener(m(o.e), o.proxy, v(o, f))
        })
    }

    function y(e, t, n, r, i) {
        var s = c(e);
        (t || "").split(/\s/).forEach(function (t) {
            h(e, t, n, r).forEach(function (t) {
                delete o[s][t.i], "removeEventListener"in e && e.removeEventListener(m(t.e), t.proxy, v(t, i))
            })
        })
    }

    function x(t, r) {
        if (r || !t.isDefaultPrevented) {
            r || (r = t), e.each(S, function (e, n) {
                var i = r[e];
                t[e] = function () {
                    return this[n] = b, i && i.apply(r, arguments)
                }, t[n] = w
            });
            if (r.defaultPrevented !== n ? r.defaultPrevented : "returnValue"in r ? r.returnValue === !1 : r.getPreventDefault && r.getPreventDefault())t.isDefaultPrevented = b
        }
        return t
    }

    function T(e) {
        var t, r = {originalEvent: e};
        for (t in e)!E.test(t) && e[t] !== n && (r[t] = e[t]);
        return x(r, e)
    }

    var t = 1, n, r = Array.prototype.slice, i = e.isFunction, s = function (e) {
        return typeof e == "string"
    }, o = {}, u = {}, a = "onfocusin"in window, f = {focus: "focusin", blur: "focusout"}, l = {
        mouseenter: "mouseover",
        mouseleave: "mouseout"
    };
    u.click = u.mousedown = u.mouseup = u.mousemove = "MouseEvents", e.event = {
        add: g,
        remove: y
    }, e.proxy = function (t, n) {
        if (i(t)) {
            var r = function () {
                return t.apply(n, arguments)
            };
            return r._zid = c(t), r
        }
        if (s(n))return e.proxy(t[n], t);
        throw new TypeError("expected function")
    }, e.fn.bind = function (e, t, n) {
        return this.on(e, t, n)
    }, e.fn.unbind = function (e, t) {
        return this.off(e, t)
    }, e.fn.one = function (e, t, n, r) {
        return this.on(e, t, n, r, 1)
    };
    var b = function () {
        return !0
    }, w = function () {
        return !1
    }, E = /^([A-Z]|returnValue$|layer[XY]$)/, S = {
        preventDefault: "isDefaultPrevented",
        stopImmediatePropagation: "isImmediatePropagationStopped",
        stopPropagation: "isPropagationStopped"
    };
    e.fn.delegate = function (e, t, n) {
        return this.on(t, e, n)
    }, e.fn.undelegate = function (e, t, n) {
        return this.off(t, e, n)
    }, e.fn.live = function (t, n) {
        return e(document.body).delegate(this.selector, t, n), this
    }, e.fn.die = function (t, n) {
        return e(document.body).undelegate(this.selector, t, n), this
    }, e.fn.on = function (t, o, u, a, f) {
        var l, c, h = this;
        if (t && !s(t))return e.each(t, function (e, t) {
            h.on(e, o, u, t, f)
        }), h;
        !s(o) && !i(a) && a !== !1 && (a = u, u = o, o = n);
        if (i(u) || u === !1)a = u, u = n;
        return a === !1 && (a = w), h.each(function (n, i) {
            f && (l = function (e) {
                return y(i, e.type, a), a.apply(this, arguments)
            }), o && (c = function (t) {
                var n, s = e(t.target).closest(o, i).get(0);
                if (s && s !== i)return n = e.extend(T(t), {
                    currentTarget: s,
                    liveFired: i
                }), (l || a).apply(s, [n].concat(r.call(arguments, 1)))
            }), g(i, t, a, u, o, c || l)
        })
    }, e.fn.off = function (t, r, o) {
        var u = this;
        return t && !s(t) ? (e.each(t, function (e, t) {
            u.off(e, r, t)
        }), u) : (!s(r) && !i(o) && o !== !1 && (o = r, r = n), o === !1 && (o = w), u.each(function () {
            y(this, t, o, r)
        }))
    }, e.fn.trigger = function (t, n) {
        return t = s(t) || e.isPlainObject(t) ? e.Event(t) : x(t), t._args = n, this.each(function () {
            "dispatchEvent"in this ? this.dispatchEvent(t) : e(this).triggerHandler(t, n)
        })
    }, e.fn.triggerHandler = function (t, n) {
        var r, i;
        return this.each(function (o, u) {
            r = T(s(t) ? e.Event(t) : t), r._args = n, r.target = u, e.each(h(u, t.type || t), function (e, t) {
                i = t.proxy(r);
                if (r.isImmediatePropagationStopped())return !1
            })
        }), i
    }, "focusin focusout load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select keydown keypress keyup error".split(" ").forEach(function (t) {
        e.fn[t] = function (e) {
            return e ? this.bind(t, e) : this.trigger(t)
        }
    }), ["focus", "blur"].forEach(function (t) {
        e.fn[t] = function (e) {
            return e ? this.bind(t, e) : this.each(function () {
                try {
                    this[t]()
                } catch (e) {
                }
            }), this
        }
    }), e.Event = function (e, t) {
        s(e) || (t = e, e = t.type);
        var n = document.createEvent(u[e] || "Events"), r = !0;
        if (t)for (var i in t)i == "bubbles" ? r = !!t[i] : n[i] = t[i];
        return n.initEvent(e, r, !0), x(n)
    }
}(Zepto), function (e) {
    function t(e) {
        var t = this.os = {}, n = this.browser = {}, r = e.match(/Web[kK]it[\/]{0,1}([\d.]+)/), i = e.match(/(Android);?[\s\/]+([\d.]+)?/), s = !!e.match(/\(Macintosh\; Intel /), o = e.match(/(iPad).*OS\s([\d_]+)/), u = e.match(/(iPod)(.*OS\s([\d_]+))?/), a = !o && e.match(/(iPhone\sOS)\s([\d_]+)/), f = e.match(/(webOS|hpwOS)[\s\/]([\d.]+)/), l = f && e.match(/TouchPad/), c = e.match(/Kindle\/([\d.]+)/), h = e.match(/Silk\/([\d._]+)/), p = e.match(/(BlackBerry).*Version\/([\d.]+)/), d = e.match(/(BB10).*Version\/([\d.]+)/), v = e.match(/(RIM\sTablet\sOS)\s([\d.]+)/), m = e.match(/PlayBook/), g = e.match(/Chrome\/([\d.]+)/) || e.match(/CriOS\/([\d.]+)/), y = e.match(/Firefox\/([\d.]+)/), b = e.match(/MSIE\s([\d.]+)/) || e.match(/Trident\/[\d](?=[^\?]+).*rv:([0-9.].)/), w = !g && e.match(/(iPhone|iPod|iPad).*AppleWebKit(?!.*Safari)/), E = w || e.match(/Version\/([\d.]+)([^S](Safari)|[^M]*(Mobile)[^S]*(Safari))/);
        if (n.webkit = !!r)n.version = r[1];
        i && (t.android = !0, t.version = i[2]), a && !u && (t.ios = t.iphone = !0, t.version = a[2].replace(/_/g, ".")), o && (t.ios = t.ipad = !0, t.version = o[2].replace(/_/g, ".")), u && (t.ios = t.ipod = !0, t.version = u[3] ? u[3].replace(/_/g, ".") : null), f && (t.webos = !0, t.version = f[2]), l && (t.touchpad = !0), p && (t.blackberry = !0, t.version = p[2]), d && (t.bb10 = !0, t.version = d[2]), v && (t.rimtabletos = !0, t.version = v[2]), m && (n.playbook = !0), c && (t.kindle = !0, t.version = c[1]), h && (n.silk = !0, n.version = h[1]), !h && t.android && e.match(/Kindle Fire/) && (n.silk = !0), g && (n.chrome = !0, n.version = g[1]), y && (n.firefox = !0, n.version = y[1]), b && (n.ie = !0, n.version = b[1]), E && (s || t.ios) && (n.safari = !0, s && (n.version = E[1])), w && (n.webview = !0), t.tablet = !!(o || m || i && !e.match(/Mobile/) || y && e.match(/Tablet/) || b && !e.match(/Phone/) && e.match(/Touch/)), t.phone = !!(!t.tablet && !t.ipod && (i || a || f || p || d || g && e.match(/Android/) || g && e.match(/CriOS\/([\d.]+)/) || y && e.match(/Mobile/) || b && e.match(/Touch/)))
    }

    t.call(e, navigator.userAgent), e.__detect = t
}(Zepto), function (e) {
    function a(e, t, n, r) {
        return Math.abs(e - t) >= Math.abs(n - r) ? e - t > 0 ? "Left" : "Right" : n - r > 0 ? "Up" : "Down"
    }

    function f() {
        s = null, t.last && (t.el.trigger("longTap"), t = {})
    }

    function l() {
        s && clearTimeout(s), s = null
    }

    function c() {
        n && clearTimeout(n), r && clearTimeout(r), i && clearTimeout(i), s && clearTimeout(s), n = r = i = s = null, t = {}
    }

    function h(e) {
        return (e.pointerType == "touch" || e.pointerType == e.MSPOINTER_TYPE_TOUCH) && e.isPrimary
    }

    function p(e, t) {
        return e.type == "pointer" + t || e.type.toLowerCase() == "mspointer" + t
    }

    var t = {}, n, r, i, s, o = 750, u;
    e(document).ready(function () {
        var d, v, m = 0, g = 0, y, b;
        "MSGesture"in window && (u = new MSGesture, u.target = document.body), e(document).bind("MSGestureEnd", function (e) {
            var n = e.velocityX > 1 ? "Right" : e.velocityX < -1 ? "Left" : e.velocityY > 1 ? "Down" : e.velocityY < -1 ? "Up" : null;
            n && (t.el.trigger("swipe"), t.el.trigger("swipe" + n))
        }).on("touchstart MSPointerDown pointerdown", function (r) {
            if ((b = p(r, "down")) && !h(r))return;
            y = b ? r : r.touches[0], r.touches && r.touches.length === 1 && t.x2 && (t.x2 = undefined, t.y2 = undefined), d = Date.now(), v = d - (t.last || d), t.el = e("tagName"in y.target ? y.target : y.target.parentNode), n && clearTimeout(n), t.x1 = y.pageX, t.y1 = y.pageY, v > 0 && v <= 250 && (t.isDoubleTap = !0), t.last = d, s = setTimeout(f, o), u && b && u.addPointer(r.pointerId)
        }).on("touchmove MSPointerMove pointermove", function (e) {
            if ((b = p(e, "move")) && !h(e))return;
            y = b ? e : e.touches[0], l(), t.x2 = y.pageX, t.y2 = y.pageY, m += Math.abs(t.x1 - t.x2), g += Math.abs(t.y1 - t.y2)
        }).on("touchend MSPointerUp pointerup", function (s) {
            if ((b = p(s, "up")) && !h(s))return;
            l(), t.x2 && Math.abs(t.x1 - t.x2) > 30 || t.y2 && Math.abs(t.y1 - t.y2) > 30 ? i = setTimeout(function () {
                t.el.trigger("swipe"), t.el.trigger("swipe" + a(t.x1, t.x2, t.y1, t.y2)), t = {}
            }, 0) : "last"in t && (m < 30 && g < 30 ? r = setTimeout(function () {
                var r = e.Event("tap");
                r.cancelTouch = c, t.el.trigger(r), t.isDoubleTap ? (t.el && t.el.trigger("doubleTap"), t = {}) : n = setTimeout(function () {
                    n = null, t.el && t.el.trigger("singleTap"), t = {}
                }, 250)
            }, 0) : t = {}), m = g = 0
        }).on("touchcancel MSPointerCancel pointercancel", c), e(window).on("scroll", c)
    }), ["swipe", "swipeLeft", "swipeRight", "swipeUp", "swipeDown", "doubleTap", "tap", "singleTap", "longTap"].forEach(function (t) {
        e.fn[t] = function (e) {
            return this.on(t, e)
        }
    })
}(Zepto), function ($) {
    function triggerAndReturn(e, t, n) {
        var r = $.Event(t);
        return $(e).trigger(r, n), !r.isDefaultPrevented()
    }

    function triggerGlobal(e, t, n, r) {
        if (e.global)return triggerAndReturn(t || document, n, r)
    }

    function ajaxStart(e) {
        e.global && $.active++ === 0 && triggerGlobal(e, null, "ajaxStart")
    }

    function ajaxStop(e) {
        e.global && !--$.active && triggerGlobal(e, null, "ajaxStop")
    }

    function ajaxBeforeSend(e, t) {
        var n = t.context;
        if (t.beforeSend.call(n, e, t) === !1 || triggerGlobal(t, n, "ajaxBeforeSend", [e, t]) === !1)return !1;
        triggerGlobal(t, n, "ajaxSend", [e, t])
    }

    function ajaxSuccess(e, t, n, r) {
        var i = n.context, s = "success";
        n.success.call(i, e, s, t), r && r.resolveWith(i, [e, s, t]), triggerGlobal(n, i, "ajaxSuccess", [t, n, e]), ajaxComplete(s, t, n)
    }

    function ajaxError(e, t, n, r, i) {
        var s = r.context;
        r.error.call(s, n, t, e), i && i.rejectWith(s, [n, t, e]), triggerGlobal(r, s, "ajaxError", [n, r, e || t]), ajaxComplete(t, n, r)
    }

    function ajaxComplete(e, t, n) {
        var r = n.context;
        n.complete.call(r, t, e), triggerGlobal(n, r, "ajaxComplete", [t, n]), ajaxStop(n)
    }

    function empty() {
    }

    function mimeToDataType(e) {
        return e && (e = e.split(";", 2)[0]), e && (e == htmlType ? "html" : e == jsonType ? "json" : scriptTypeRE.test(e) ? "script" : xmlTypeRE.test(e) && "xml") || "text"
    }

    function appendQuery(e, t) {
        return t == "" ? e : (e + "&" + t).replace(/[&?]{1,2}/, "?")
    }

    function serializeData(e) {
        e.processData && e.data && $.type(e.data) != "string" && (e.data = $.param(e.data, e.traditional)), e.data && (!e.type || e.type.toUpperCase() == "GET") && (e.url = appendQuery(e.url, e.data), e.data = undefined)
    }

    function parseArguments(e, t, n, r) {
        return $.isFunction(t) && (r = n, n = t, t = undefined), $.isFunction(n) || (r = n, n = undefined), {
            url: e,
            data: t,
            success: n,
            dataType: r
        }
    }

    function serialize(e, t, n, r) {
        var i, s = $.isArray(t), o = $.isPlainObject(t);
        $.each(t, function (t, u) {
            i = $.type(u), r && (t = n ? r : r + "[" + (o || i == "object" || i == "array" ? t : "") + "]"), !r && s ? e.add(u.name, u.value) : i == "array" || !n && i == "object" ? serialize(e, u, n, t) : e.add(t, u)
        })
    }

    var jsonpID = 0, document = window.document, key, name, rscript = /<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi, scriptTypeRE = /^(?:text|application)\/javascript/i, xmlTypeRE = /^(?:text|application)\/xml/i, jsonType = "application/json", htmlType = "text/html", blankRE = /^\s*$/;
    $.active = 0, $.ajaxJSONP = function (e, t) {
        if ("type"in e) {
            var n = e.jsonpCallback, r = ($.isFunction(n) ? n() : n) || "jsonp" + ++jsonpID, i = document.createElement("script"), s = window[r], o, u = function (e) {
                $(i).triggerHandler("error", e || "abort")
            }, a = {abort: u}, f;
            return t && t.promise(a), $(i).on("load error", function (n, u) {
                clearTimeout(f), $(i).off().remove(), n.type == "error" || !o ? ajaxError(null, u || "error", a, e, t) : ajaxSuccess(o[0], a, e, t), window[r] = s, o && $.isFunction(s) && s(o[0]), s = o = undefined
            }), ajaxBeforeSend(a, e) === !1 ? (u("abort"), a) : (window[r] = function () {
                o = arguments
            }, i.src = e.url.replace(/\?(.+)=\?/, "?$1=" + r), document.head.appendChild(i), e.timeout > 0 && (f = setTimeout(function () {
                u("timeout")
            }, e.timeout)), a)
        }
        return $.ajax(e)
    }, $.ajaxSettings = {
        type: "GET",
        beforeSend: empty,
        success: empty,
        error: empty,
        complete: empty,
        context: null,
        global: !0,
        xhr: function () {
            return new window.XMLHttpRequest
        },
        accepts: {
            script: "text/javascript, application/javascript, application/x-javascript",
            json: jsonType,
            xml: "application/xml, text/xml",
            html: htmlType,
            text: "text/plain"
        },
        crossDomain: !1,
        timeout: 0,
        processData: !0,
        cache: !0
    }, $.ajax = function (options) {
        var settings = $.extend({}, options || {}), deferred = $.Deferred && $.Deferred();
        for (key in $.ajaxSettings)settings[key] === undefined && (settings[key] = $.ajaxSettings[key]);
        ajaxStart(settings), settings.crossDomain || (settings.crossDomain = /^([\w-]+:)?\/\/([^\/]+)/.test(settings.url) && RegExp.$2 != window.location.host), settings.url || (settings.url = window.location.toString()), serializeData(settings), settings.cache === !1 && (settings.url = appendQuery(settings.url, "_=" + Date.now()));
        var dataType = settings.dataType, hasPlaceholder = /\?.+=\?/.test(settings.url);
        if (dataType == "jsonp" || hasPlaceholder)return hasPlaceholder || (settings.url = appendQuery(settings.url, settings.jsonp ? settings.jsonp + "=?" : settings.jsonp === !1 ? "" : "callback=?")), $.ajaxJSONP(settings, deferred);
        var mime = settings.accepts[dataType], headers = {}, setHeader = function (e, t) {
            headers[e.toLowerCase()] = [e, t]
        }, protocol = /^([\w-]+:)\/\//.test(settings.url) ? RegExp.$1 : window.location.protocol, xhr = settings.xhr(), nativeSetHeader = xhr.setRequestHeader, abortTimeout;
        deferred && deferred.promise(xhr), settings.crossDomain || setHeader("X-Requested-With", "XMLHttpRequest"), setHeader("Accept", mime || "*/*");
        if (mime = settings.mimeType || mime)mime.indexOf(",") > -1 && (mime = mime.split(",", 2)[0]), xhr.overrideMimeType && xhr.overrideMimeType(mime);
        (settings.contentType || settings.contentType !== !1 && settings.data && settings.type.toUpperCase() != "GET") && setHeader("Content-Type", settings.contentType || "application/x-www-form-urlencoded");
        if (settings.headers)for (name in settings.headers)setHeader(name, settings.headers[name]);
        xhr.setRequestHeader = setHeader, xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                xhr.onreadystatechange = empty, clearTimeout(abortTimeout);
                var result, error = !1;
                if (xhr.status >= 200 && xhr.status < 300 || xhr.status == 304 || xhr.status == 0 && protocol == "file:") {
                    dataType = dataType || mimeToDataType(settings.mimeType || xhr.getResponseHeader("content-type")), result = xhr.responseText;
                    try {
                        dataType == "script" ? (1, eval)(result) : dataType == "xml" ? result = xhr.responseXML : dataType == "json" && (result = blankRE.test(result) ? null : $.parseJSON(result))
                    } catch (e) {
                        error = e
                    }
                    error ? ajaxError(error, "parsererror", xhr, settings, deferred) : ajaxSuccess(result, xhr, settings, deferred)
                } else ajaxError(xhr.statusText || null, xhr.status ? "error" : "abort", xhr, settings, deferred)
            }
        };
        if (ajaxBeforeSend(xhr, settings) === !1)return xhr.abort(), ajaxError(null, "abort", xhr, settings, deferred), xhr;
        if (settings.xhrFields)for (name in settings.xhrFields)xhr[name] = settings.xhrFields[name];
        var async = "async"in settings ? settings.async : !0;
        xhr.open(settings.type, settings.url, async, settings.username, settings.password);
        for (name in headers)nativeSetHeader.apply(xhr, headers[name]);
        return settings.timeout > 0 && (abortTimeout = setTimeout(function () {
            xhr.onreadystatechange = empty, xhr.abort(), ajaxError(null, "timeout", xhr, settings, deferred)
        }, settings.timeout)), xhr.send(settings.data ? settings.data : null), xhr
    }, $.get = function () {
        return $.ajax(parseArguments.apply(null, arguments))
    }, $.post = function () {
        var e = parseArguments.apply(null, arguments);
        return e.type = "POST", $.ajax(e)
    }, $.getJSON = function () {
        var e = parseArguments.apply(null, arguments);
        return e.dataType = "json", $.ajax(e)
    }, $.fn.load = function (e, t, n) {
        if (!this.length)return this;
        var r = this, i = e.split(/\s/), s, o = parseArguments(e, t, n), u = o.success;
        return i.length > 1 && (o.url = i[0], s = i[1]), o.success = function (e) {
            r.html(s ? $("<div>").html(e.replace(rscript, "")).find(s) : e), u && u.apply(r, arguments)
        }, $.ajax(o), this
    };
    var escape = encodeURIComponent;
    $.param = function (e, t) {
        var n = [];
        return n.add = function (e, t) {
            this.push(escape(e) + "=" + escape(t))
        }, serialize(n, e, t), n.join("&").replace(/%20/g, "+")
    }
}(Zepto), function (e) {
    e.Callbacks = function (t) {
        t = e.extend({}, t);
        var n, r, i, s, o, u, a = [], f = !t.once && [], l = function (e) {
            n = t.memory && e, r = !0, u = s || 0, s = 0, o = a.length, i = !0;
            for (; a && u < o; ++u)if (a[u].apply(e[0], e[1]) === !1 && t.stopOnFalse) {
                n = !1;
                break
            }
            i = !1, a && (f ? f.length && l(f.shift()) : n ? a.length = 0 : c.disable())
        }, c = {
            add: function () {
                if (a) {
                    var r = a.length, u = function (n) {
                        e.each(n, function (e, n) {
                            typeof n == "function" ? (!t.unique || !c.has(n)) && a.push(n) : n && n.length && typeof n != "string" && u(n)
                        })
                    };
                    u(arguments), i ? o = a.length : n && (s = r, l(n))
                }
                return this
            }, remove: function () {
                return a && e.each(arguments, function (t, n) {
                    var r;
                    while ((r = e.inArray(n, a, r)) > -1)a.splice(r, 1), i && (r <= o && --o, r <= u && --u)
                }), this
            }, has: function (t) {
                return !!a && !!(t ? e.inArray(t, a) > -1 : a.length)
            }, empty: function () {
                return o = a.length = 0, this
            }, disable: function () {
                return a = f = n = undefined, this
            }, disabled: function () {
                return !a
            }, lock: function () {
                return f = undefined, n || c.disable(), this
            }, locked: function () {
                return !f
            }, fireWith: function (e, t) {
                return a && (!r || f) && (t = t || [], t = [e, t.slice ? t.slice() : t], i ? f.push(t) : l(t)), this
            }, fire: function () {
                return c.fireWith(this, arguments)
            }, fired: function () {
                return !!r
            }
        };
        return c
    }
}(Zepto), function (e) {
    function n(t) {
        var r = [["resolve", "done", e.Callbacks({
            once: 1,
            memory: 1
        }), "resolved"], ["reject", "fail", e.Callbacks({
            once: 1,
            memory: 1
        }), "rejected"], ["notify", "progress", e.Callbacks({memory: 1})]], i = "pending", s = {
            state: function () {
                return i
            }, always: function () {
                return o.done(arguments).fail(arguments), this
            }, then: function () {
                var t = arguments;
                return n(function (n) {
                    e.each(r, function (r, i) {
                        var u = e.isFunction(t[r]) && t[r];
                        o[i[1]](function () {
                            var t = u && u.apply(this, arguments);
                            if (t && e.isFunction(t.promise))t.promise().done(n.resolve).fail(n.reject).progress(n.notify); else {
                                var r = this === s ? n.promise() : this, o = u ? [t] : arguments;
                                n[i[0] + "With"](r, o)
                            }
                        })
                    }), t = null
                }).promise()
            }, promise: function (t) {
                return t != null ? e.extend(t, s) : s
            }
        }, o = {};
        return e.each(r, function (e, t) {
            var n = t[2], u = t[3];
            s[t[1]] = n.add, u && n.add(function () {
                i = u
            }, r[e ^ 1][2].disable, r[2][2].lock), o[t[0]] = function () {
                return o[t[0] + "With"](this === o ? s : this, arguments), this
            }, o[t[0] + "With"] = n.fireWith
        }), s.promise(o), t && t.call(o, o), o
    }

    var t = Array.prototype.slice;
    e.when = function (r) {
        var i = t.call(arguments), s = i.length, o = 0, u = s !== 1 || r && e.isFunction(r.promise) ? s : 0, a = u === 1 ? r : n(), f, l, c, h = function (e, n, r) {
            return function (i) {
                n[e] = this, r[e] = arguments.length > 1 ? t.call(arguments) : i, r === f ? a.notifyWith(n, r) : --u || a.resolveWith(n, r)
            }
        };
        if (s > 1) {
            f = new Array(s), l = new Array(s), c = new Array(s);
            for (; o < s; ++o)i[o] && e.isFunction(i[o].promise) ? i[o].promise().done(h(o, c, i)).fail(a.reject).progress(h(o, l, f)) : --u
        }
        return u || a.resolveWith(c, i), a.promise()
    }, e.Deferred = n
}(Zepto), define("zepto", function (e) {
    return function () {
        var t, n;
        return t || e.Zepto
    }
}(this)), !function (e) {
    function t(e, t) {
        return (/string|function/.test(typeof t) ? a : u)(e, t)
    }

    function n(e, t) {
        return "string" != typeof e && (t = typeof e, "number" === t ? e += "" : e = "function" === t ? n(e.call(e)) : ""), e
    }

    function r(e) {
        return c[e]
    }

    function i(e) {
        return n(e).replace(/&(?![\w#]+;)|[<>"']/g, r)
    }

    function s(e, t) {
        if (h(e))for (var n = 0, r = e.length; r > n; n++)t.call(e, e[n], n, e); else for (n in e)t.call(e, e[n], n)
    }

    function o(e, t) {
        var n = /(\/)[^/]+\1\.\.\1/, r = ("./" + e).replace(/[^/]+$/, ""), i = r + t;
        for (i = i.replace(/\/\.\//g, "/"); i.match(n);)i = i.replace(n, "/");
        return i
    }

    function u(e, n) {
        var r = t.get(e) || f({filename: e, name: "Render Error", message: "Template not found"});
        return n ? r(n) : r
    }

    function a(t, n) {
        if ("string" == typeof n) {
            var r = n;
            n = function () {
                return new e(r)
            }
        }
        var i = l[t] = function (e) {
            try {
                return new n(e, t) + ""
            } catch (r) {
                return f(r)()
            }
        };
        return i.prototype = n.prototype = p, i.toString = function () {
            return n + ""
        }, i
    }

    function f(e) {
        var t = "{Template Error}", n = e.stack || "";
        if (n)n = n.split("\n").slice(0, 2).join("\n"); else for (var r in e)n += "<" + r + ">\n" + e[r] + "\n\n";
        return function () {
            return "object" == typeof console && console.error(t + "\n\n" + n), t
        }
    }

    var l = t.cache = {}, e = this.String, c = {
        "<": "&#60;",
        ">": "&#62;",
        '"': "&#34;",
        "'": "&#39;",
        "&": "&#38;"
    }, h = Array.isArray || function (e) {
            return "[object Array]" === {}.toString.call(e)
        }, p = t.utils = {
        $helpers: {}, $include: function (e, t, n) {
            return e = o(n, e), u(e, t)
        }, $string: n, $escape: i, $each: s
    }, d = t.helpers = p.$helpers;
    t.get = function (e) {
        return l[e.replace(/^\.\//, "")]
    }, t.helper = function (e, t) {
        d[e] = t
    }, "function" == typeof define ? define("template", [], function () {
        return t
    }) : "undefined" != typeof exports ? module.exports = t : this.template = t, t("braingame", '<div class="step step-2"> <div class="title"><h3></h3><p></p></div> <img class="board" src="http://r2.ykimg.com/05100000547C33716714C02441001208" width="100%" height="100%" alt="" title="" /> <div class="thegame"> <table cellspacing="0" id="gridbox"> </table> </div> <img class="start_btn" id="grid_remember" src="http://r1.ykimg.com/05100000547D72686714C028F40B873F" width="100%" height="100%" alt="" title="" /> <div class="cutting-btn"></div> </div> '), t("braingame_check", '<div class="step step-2 braingame-check"> <div class="title"><h3></h3><p></p></div> <img class="board" src="http://r2.ykimg.com/05100000547C33716714C02441001208" width="100%" height="100%" alt="" title="" /> <div class="thegame"> <table cellspacing="0" id="gridbox"> </table> </div> </div> '), t("braingame_start", '<div class="step step-1"> <img class="bg" src="http://r3.ykimg.com/05100000547BE63A6714C06BD70AC2E1" width="100%" height="100" alt="" title="" /> <img class="board" src="http://r2.ykimg.com/05100000547BE6556714C06B3604915A" width="100%" height="100" alt="" title="" /> <img class="start" src="http://r4.ykimg.com/05100000547BE66F6714C078CB09B15E" width="100%" height="100" alt="" title="" /> <img class="start_btn braingame_start" src="http://r3.ykimg.com/05100000547BE6816714C00F4D052B3D" width="100%" height="100" alt="" title="" /> <div class="cutting-btn"></div> </div> '), t("braingame_success", function (t) {
        var n = this, r = (n.$helpers, n.$escape), i = t._nickname, s = t.beatplayer, o = t._time, u = t._level, a = t._overfriend, f = "";
        return f += '<div class="step step-3"> <img class="bg" src="http://r3.ykimg.com/05100000547BE63A6714C06BD70AC2E1" width="100%" height="100" alt="" title="" /> <img class="board" src="http://www.laibican.com/images/games/brain/0510000054924B536714C00CD80049B7.png" width="100%" height="100%" alt="" title="" /> <img class="award" src="http://r2.ykimg.com/05100000547D84D96714C01D3C04FC17" width="100%" height="100%" alt="" title="" /> <div class="thegame-over"> <div class="chenghao"> <label>您的称号</label> <h1>', f += r(i), f += '</h1> </div> <div class="tips"> / 击败最强大脑', f += r(s), f += '玩家 / </div> <div class="rank"> <p>通关级数: ', f += r(o), f += "秒闯过", f += r(u), f += "关</p> <p>您的好友刚刚闯过", f += r(a), f += '关</p> </div> </div>  <img src="http://r4.ykimg.com/05100000548E93836714C0780100DCC8" alt="" width="100%" height="100%" class="start_btn" id="userMap" title="" /> <div class="start-game-btn game-again-btn"></div> <div class="start-game-btn game-share-btn"></div> </div> ', new e(f)
    })
}();
var BraingameConfig = {
    1: [1, 4, 4, 1],
    2: [2, 9, 3, 1],
    3: [1, 9, 3, 1],
    4: [2, 9, 4, 1],
    5: [2, 9, 5, 1],
    6: [1, 9, 6, 1],
    7: [2, 9, 7, 1],
    8: [1, 9, 8, 1],
    9: [2, 9, 8, 1],
    10: [2, 9, 9, 1],
    11: [1, 16, 3, 1],
    12: [2, 16, 3, 1],
    13: [1, 16, 4, 1],
    14: [1, 16, 5, 1],
    15: [2, 16, 6, 1],
    16: [1, 16, 7, 1],
    17: [1, 16, 8, 1],
    18: [2, 16, 8, 1],
    19: [1, 16, 9, 1],
    20: [2, 16, 9, 1]
}, tapholdImg = ["http://r4.ykimg.com/051000005485798B6714C007A909F4B4", "http://r1.ykimg.com/05100000548572AC6714C05F5E0B6817", "http://r4.ykimg.com/05100000548E93336714C05FCA06AFC5", "http://r4.ykimg.com/05100000548E94C36714C044F30598FC"], beatPlayer = {
    1: "1%",
    2: "10%",
    3: "10%",
    4: "15%",
    5: "20%",
    6: "30%",
    7: "35%",
    8: "40%",
    9: "45%",
    10: "50%",
    11: "55%",
    12: "60%",
    13: "65%",
    14: "70%",
    15: "75%",
    16: "80%",
    17: "85%",
    18: "90%",
    19: "95%",
    20: "100%"
}, checkpointsStatistic = ["4009966", "4009967", "4009968", "4009969", "4009970", "4009971", "4009972", "4009973", "4009974", "4009975", "4009976", "4009977", "4009978", "4009979", "4009980", "4009981", "4009982", "4009983", "4009984", "4009985"], otherStatistic = ["4009964", "4009965"], c = 60, t, _time, _colorLibrary = {
    0: ["#38b7b1", "#b6ca27", "#38b7b1", "#faea99", "#74979f", "#38b7b1", "#fef1c9", "http://r3.ykimg.com/05100000547C2D526714C06D30072036"],
    1: ["#85bca4", "#ffe400", "#85bca4", "#b6d7b6", "#599e80", "#85bca4", "#8fc78f", "http://r3.ykimg.com/0510000054890B666714C00B65093557"],
    2: ["#ca584f", "#ff9c91", "#ca584f", "#faea99", "#8b3130", "#ca584f", "#f3feed", "http://r1.ykimg.com/0510000054890B846714C05D29089A39"],
    3: ["#b6fc7e", "#ffa700", "#b6fc7e", "#faea99", "#85ca4e", "#b6fc7e", "#fef1c9", "http://r1.ykimg.com/0510000054890B986714C063F2000F6C"],
    4: ["#46ada0", "#3b3a7e", "#46ada0", "#ddf1c2", "#15786c", "#46ada0", "#e9d8ff", "http://r2.ykimg.com/0510000054890BA56714C01BD601537C"]
}, GameColor = ["#f48383", "#ffde00", "#38b7b1", "#79e324", "#fd784a", "#e9d8dd", "#ffe94e", "#8ed6f7", "#ce3220", "#b6d7b6", "#2ea5e4"], Gametype = [1, 2], Gamegrid = [4, 9, 16], GameResult = [1];
define("braingame", ["zepto", "template"], function (e, n) {
    function i(e) {
        var t = new RegExp("(^|&)" + e + "=([^&]*)(&|$)", "i"), n = window.location.search.substr(1).match(t);
        return n != null ? unescape(n[2]) : null
    }

    function s(e, t) {
        var n = t - e, r = Math.random();
        return e + Math.round(r * n)
    }

    function o(e, t) {
        var n = new RegExp("(^| )" + t + "( |$)");
        e.className = e.className.replace(n, " ").replace(/^\s+|\s+$/g, "")
    }

    function u(e, t) {
        a(e, t) || (e.className += " " + t)
    }

    function a(e, t) {
        var n = new RegExp("(^| )" + t + "( |$)");
        return n.test(e.className)
    }

    var r = {
        level: 1, init: function () {
            if (i("level") && !isNaN(parseInt(i("level"))) && BraingameConfig[i("level")])this._braingameRendrerData(); else {
                if (i("jump") && i("jump") == "success")return r._jumpSuccess(), !1;
                this._introduction()
            }
        }, _initParams: function () {
            var e = i("level"), t = i("type"), n = i("grid"), s = i("num"), o = i("re");
            e && !isNaN(e) && BraingameConfig[e] && (r.level = e), t && !isNaN(t) && Gametype.indexOf(parseInt(t)) >= 0 ? r.type = t : r.type = BraingameConfig[r.level][0], n && !isNaN(n) && Gamegrid.indexOf(parseInt(n)) >= 0 ? r.grid = n : r.grid = BraingameConfig[r.level][1], s && !isNaN(s) && parseInt(s) < 10 && parseInt(s) >= 2 ? r.num = s : r.num = BraingameConfig[r.level][2], o && !isNaN(o) && GameResult.indexOf(parseInt(o)) >= 0 ? r.re = o : r.re = BraingameConfig[r.level][3], console.log(r.level), console.log(r.type), console.log(r.grid), console.log(r.num), console.log(r.re)
        }, _introduction: function () {
            var t = n("braingame_start", {});
            e(".yk-grid").html(t), r.setGameData(""), r.sendStatistic(otherStatistic[0]), e(".cutting-btn").on("longTap", function () {
                e(".braingame_start").attr("src", tapholdImg[0])
            }), this._bind()
        }, _bind: function () {
            e(".cutting-btn").on("tap", this._braingameRendrerData)
        }, _braingameRendrerData: function () {
            document.getElementById("brain_clock").style.display = "block", t || r._timedCount(), r._initParams(), r.type == 2 && e("body").css("background", "#faea99"), r.type == 1 ? r.answerValue = r._answerValueNum(r.re) : r.type == 2 && (r.answerValue = r._answerValueColor(r.re));
            var n = r.grid - r.answerValue.length, i = r.num - r.answerValue.length;
            console.log(r.answerValue), console.log(n), console.log(i);
            if (r.type == 1) {
                var o = r._getDiffNumValue(i, r.answerValue);
                console.log(o);
                var u = r.answerValue.concat(o);
                console.log(u);
                var a = r.grid - u.length, f = o;
                for (var l = 0; l < a; l++) {
                    var c = f.length - 1;
                    o.push(f[s(0, c)])
                }
                r.endConcatTemplateData = r.answerValue.concat(o), console.log(r.endConcatTemplateData), r.endConcatTemplateData = r._sortData(r.endConcatTemplateData), console.log(r.endConcatTemplateData)
            } else if (r.type == 2) {
                var h = r._getDiffColorValue(i, r.answerValue);
                console.log(h);
                var u = r.answerValue.concat(h);
                console.log(u);
                var p = r.grid - u.length, d = h;
                for (var v = 0; v < p; v++) {
                    var m = d.length - 1;
                    h.push(d[s(0, m)])
                }
                r.endConcatTemplateData = r.answerValue.concat(h), console.log(r.endConcatTemplateData), r.endConcatTemplateData = r._sortData(r.endConcatTemplateData), console.log(r.endConcatTemplateData)
            }
            r._gameTemplate(r.endConcatTemplateData)
        }, _gameTemplate: function (t) {
            var i = n("braingame", {});
            e(".yk-grid").html(i), r._renderTemplate(t, 1), e(".cutting-btn").on("longTap", function () {
                e("#grid_remember").attr("src", tapholdImg[1])
            }), e(".cutting-btn").on("tap", r._gameCheckTemplate), r.RandomForDiffColor = s(0, 4), console.log(r.RandomForDiffColor);
            var o = e("#gridbox tr td"), u = e("body"), a = e("title");
            r.type == 1 && (o.css({
                color: _colorLibrary[r.RandomForDiffColor][0],
                background: _colorLibrary[r.RandomForDiffColor][1],
                border: "1px solid " + _colorLibrary[r.RandomForDiffColor][2]
            }), u.css({background: _colorLibrary[r.RandomForDiffColor][3]})), r.type == 1 && r.gridFontSize(r.grid), e(".clock img").attr("src", _colorLibrary[r.RandomForDiffColor][7])
        }, _renderTemplate: function (t, n) {
            e("#braingame-answer").css("background-color", ""), e(".title h3").html("第" + r.level + "关"), n == 1 && (r.type == 1 && r.level == 1 && e(".title p").html("请记住下面数字的位置"), r.type == 2 && r.level == 2 && e(".title p").html("请记住下面颜色块的位置"), e(".title p").css({"margin-left": "0px"}));
            var i = 2;
            r.grid == 4 ? i = r.grid / 2 : r.grid == 9 ? i = r.grid / 3 : r.grid == 16 && (i = r.grid / 4);
            var s = "";
            for (var o = 0; o < i; o++) {
                s += "<tr>";
                var u = "";
                for (var a = 0; a < i; a++)u += "<td></td>";
                s += u + "</tr>"
            }
            e("#gridbox").html(s);
            var f = e("#gridbox td").length;
            if (r.type == 1)for (var o = 0; o < f; o++)n == 1 && e("#gridbox td").eq(o).html(t[o]), e("#gridbox td").eq(o).attr("data-name", "grid_" + o); else if (r.type == 2)for (var o = 0; o < f; o++)n == 1 && e("#gridbox td").eq(o).css("background-color", t[o]), e("#gridbox td").eq(o).attr("data-name", "grid_" + o)
        }, _gameCheckTemplate: function () {
            var t = n("braingame_check", {});
            e(".yk-grid").html(t), r._renderTemplate(r.endConcatTemplateData, 2), r.type == 2 && e("#gridbox td").css({
                "background-color": "#64C5B2",
                color: "#64C5B2"
            });
            var i = e("#gridbox tr td"), s = e("title");
            r.type == 1 && i.css({
                background: _colorLibrary[r.RandomForDiffColor][5],
                border: "1px solid " + _colorLibrary[r.RandomForDiffColor][4]
            }), r.type == 1 && r.gridFontSize(r.grid), e("#gridbox td").on("tap", r._doChecked), r._renderAnswer()
        }, _doChecked: function () {
            var t = e(this).attr("data-name");
            t = t.split("_")[1];
            if (r.answerValue.indexOf(r.endConcatTemplateData[t]) >= 0) {
                r.level = parseInt(r.level) + 1;
                if (!BraingameConfig[r.level])return r._stopCount(), e("#brain_clock").remove(), r.level == 21 && (r.level = 20), r.setGameData(JSON.stringify({
                    level: r.level,
                    _time: _time
                })), setTimeout(function () {
                    r._linkSuccess()
                }, 500), !1;
                i("level") && !isNaN(parseInt(i("level"))) && BraingameConfig[i("level")] ? window.location.href = "http://" + window.location.host + window.location.pathname + "?level=" + r.level : r._braingameRendrerData(), e("#braingame-answer").html("")
            } else r._stopCount(), e("#brain_clock").remove(), r._appendError(), r.setGameData(JSON.stringify({
                level: r.level,
                _time: _time
            })), setTimeout(function () {
                r._linkSuccess()
            }, 2e3)
        }, _jumpSuccess: function () {
            var t = r.getLocalStorage("currentGamedata");
            if (t) {
                t = JSON.parse(t);
                var i = t.level, o = t._time
            }
            i && i > 0 && r.sendStatistic(checkpointsStatistic[i - 1]);
            var u = [-2, -1, 1, 2, 3, 4, 5, 6], a = s(0, 7), f = Math.abs(i + u[a]) != 0 ? Math.abs(i + u[a]) : 10;
            f > 20 && (f = 20);
            var l = {
                _level: i,
                _time: o,
                _nickname: r._nickName(i),
                beatplayer: beatPlayer[i],
                _overfriend: f
            }, c = n("braingame_success", l);
            e(".yk-grid").html(c), e(".game-again-btn").on("tap", r._againGame), r.isWeixinBrowser() && e(".game-share-btn").on("tap", function () {
                r._appendOverlay(), r._appendShare(), setTimeout(function () {
                    e(".overlay").remove(), e(".share-overlay").remove()
                }, 2e3)
            }), e(".game-again-btn").on("longTap", function () {
                e(".start_btn").attr("src", tapholdImg[2])
            }), e(".game-share-btn").on("longTap", function () {
                e(".start_btn").attr("src", tapholdImg[3])
            })
        }, _againGame: function () {
            window.location.href = "http://" + window.location.host + window.location.pathname
        }, _linkSuccess: function () {
            window.location.href = "http://" + window.location.host + window.location.pathname + "?jump=success"
        }, _renderAnswer: function () {
            for (var t = 0; t < r.answerValue.length; t++)if (r.type == 1) {
                var n = "请找出数字:" + r.answerValue[t];
                e(".title p").html(n)
            } else if (r.type == 2) {
                var n = '<span style="margin-left:20%;">请找出颜色块:</span><span class="answer-color-grid"></span>';
                e(".title p").html(n), e(".title .answer-color-grid").css({
                    "background-color": r.answerValue[t],
                    border: "2px solid #38b7b1",
                    width: "30px",
                    height: "30px",
                    display: "block",
                    "float": "right",
                    "margin-right": "24%",
                    "margin-top": "-2%"
                })
            }
        }, _appendOverlay: function () {
            e(".yk-grid").append('<div class="overlay" id="overlay"></div>')
        }, _appendGameover: function () {
            e(".yk-grid").append('<div class="gameover"><img class="gameover-img" src="http://r4.ykimg.com/05100000547D8BA76714C011DA0301FF" width="100%" height="100%" alt="" title=""></div>')
        }, _appendError: function () {
            r._renderTemplate(r.endConcatTemplateData, 1), e(".title p").remove(), r.type == 1 && r.gridFontSize(r.grid), e(".braingame-check").append('<img class="error" src="http://r3.ykimg.com/05100000547EDE7E6714C00B900C70FF" width="100%" height="100%" alt="" title="">')
        }, _appendShare: function () {
            e(".yk-grid").append('<div class="share share-overlay" id="share-overlay"><img class="share-img" src="http://r1.ykimg.com/0510000054800D806714C0452907BA24" width="100%" height="100%" alt="" title=""></div>')
        }, _getDiffNumValue: function (e, t) {
            var n = [];
            while (n.length < e) {
                var r = s(0, 9);
                n.indexOf(r) < 0 && t.indexOf(r) < 0 && n.push(r)
            }
            return n
        }, _getDiffColorValue: function (e, t) {
            var n = [];
            while (n.length < e) {
                var r = GameColor[s(0, 10)];
                n.indexOf(r) < 0 && t.indexOf(r) < 0 && n.push(r)
            }
            return n
        }, _answerValueNum: function (e) {
            var t = [];
            for (var n = 0; n < e; n++)t.push(s(0, 9));
            return t
        }, _answerValueColor: function (e) {
            var t = [];
            for (var n = 0; n < e; n++)t.push(GameColor[s(0, 10)]);
            return t
        }, _sortData: function (e) {
            return e.sort(function () {
                return .5 - Math.random()
            })
        }, _timedCount: function () {
            document.getElementById("time-conut").innerHTML = c, t = setInterval(function () {
                if (!(c >= 0))return r._appendOverlay(), r._appendGameover(), r._stopCount(), e("#brain_clock").remove(), r.setGameData(JSON.stringify({
                    level: r.level,
                    _time: _time
                })), setTimeout(function () {
                    r._linkSuccess()
                }, 2e3), !1;
                c -= 1;
                if (c <= 10) {
                    var t = document.getElementById("brain_clock");
                    u(t, "clock-animation")
                }
                c >= 0 && (c < 10 && (c = "0" + c), document.getElementById("time-conut").innerHTML = c)
            }, 1e3)
        }, _stopCount: function () {
            c = 60, clearTimeout(t), _time = 60 - document.getElementById("time-conut").innerHTML, document.getElementById("time-conut").innerHTML = ""
        }, _nickName: function (e) {
            var t = "";
            return e >= 1 && e <= 2 ? t = "记忆师学徒" : e >= 3 && e <= 5 ? t = "初级记忆师" : e >= 6 && e <= 8 ? t = "中级记忆师" : e >= 9 && e <= 11 ? t = "高级记忆师" : e >= 12 && e <= 14 ? t = "特级记忆师" : e >= 15 && e <= 17 ? t = "神级记忆师" : e >= 18 && e <= 20 && (t = "无敌记忆王"), t
        }, isWeixinBrowser: function () {
            var e = navigator.userAgent.toLowerCase();
            return e.match(/MicroMessenger/i) == "micromessenger" ? !0 : !1
        }, setLocalStorage: function (e, t) {
            return window.sessionStorage && window.sessionStorage.getItem ? window.sessionStorage.setItem(e, t) : !1
        }, getLocalStorage: function (e) {
            return window.sessionStorage && window.sessionStorage.getItem ? window.sessionStorage.getItem(e) : !1
        }, setGameData: function (e) {
            r.setLocalStorage("currentGamedata", e)
        }, gridFontSize: function (t) {
            t == 4 ? e(".thegame td").css("font-size", "48px") : t == 9 ? e(".thegame td").css("font-size", "32px") : t == 16 && e(".thegame td").css("font-size", "24px")
        }, sendStatistic: function (e) {
        }
    };
    return r
}), require.config({
    shim: {zepto: {exports: "Zepto"}, "zepto.scroll": {deps: ["zepto"], exports: "$.fn.scrollTo"}},
    urlArgs: "bust=" + (new Date).getTime()
}), define("main", ["braingame"], function (e) {
    e.init()
});