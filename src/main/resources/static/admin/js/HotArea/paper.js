(function webpackUniversalModuleDefinition(root, factory) {
    if(typeof exports === 'object' && typeof module === 'object')
        module.exports = factory(require("Raphael"), require("eve"));
    else if(typeof define === 'function' && define.amd)
        define(["Raphael", "eve"], factory);
    else if(typeof exports === 'object')
        exports["Paper"] = factory(require("Raphael"), require("eve"));
    else
        root["Paper"] = factory(root["Raphael"], root["eve"]);
})(this, function(__WEBPACK_EXTERNAL_MODULE_2__, __WEBPACK_EXTERNAL_MODULE_4__) {
return /******/ (function(modules) { // webpackBootstrap
/******/    // The module cache
/******/    var installedModules = {};

/******/    // The require function
/******/    function __webpack_require__(moduleId) {

/******/        // Check if module is in cache
/******/        if(installedModules[moduleId])
/******/            return installedModules[moduleId].exports;

/******/        // Create a new module (and put it into the cache)
/******/        var module = installedModules[moduleId] = {
/******/            exports: {},
/******/            id: moduleId,
/******/            loaded: false
/******/        };

/******/        // Execute the module function
/******/        modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/        // Flag the module as loaded
/******/        module.loaded = true;

/******/        // Return the exports of the module
/******/        return module.exports;
/******/    }


/******/    // expose the modules object (__webpack_modules__)
/******/    __webpack_require__.m = modules;

/******/    // expose the module cache
/******/    __webpack_require__.c = installedModules;

/******/    // __webpack_public_path__
/******/    __webpack_require__.p = "";

/******/    // Load entry module and return exports
/******/    return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

    'use strict';

    /*
     *************************
     * 引入模块
     *************************
     */

    var Brush = __webpack_require__(1),
        eventEmitter = __webpack_require__(3),
        rect = __webpack_require__(5),
        polygon = __webpack_require__(6),
        modify = __webpack_require__(7),
        overlap = __webpack_require__(8);

    /*
     *************************
     * 定义【Paper】类
     *************************
     */

    function Paper(id) {
        // 设置基本属性
        this.state = 'pending';
        this.brush = new Brush(id);
        this.canvas = this.brush.canvas;
        this.wrapper = document.getElementById(id);
        this.events = eventEmitter(this.wrapper);
        this.anchors = this.brush.set();
        this.polygons = this.brush.set();
        this.activePolygon = null;

        // 添加图形绘制模块
        this.adopt(rect);
        this.adopt(polygon);
        this.adopt(modify);
    }

    // 设置【Paper】方法
    Paper.prototype = {
        constructor: Paper,
        blur: function blur() {
            this.activePolygon = null;
            this.anchors.forEach(function (v) {
                return v.remove();
            });
            this.anchors.clear();
            return this;
        },
        focus: function focus(points) {
            points = points || this.activePolygon;

            if (!points) {
                return this;
            }

            this.blur();

            var color = void 0;

            if (points.utype === 'polygon') {
                this.activePolygon = points;
                color = points.style && points.style.stroke;
                points = points.points;
            }

            var len = points.length,
                i = 0,
                p = void 0;

            for (; i < len; i++) {
                p = points[i];
                this.anchor(p[0], p[1]);
            }

            color && this.anchors.attr('fill', color);

            return this;
        },
        getAnchorByNode: function getAnchorByNode(node) {
            var el = null;

            this.anchors.forEach(function (v) {
                return v.node === node ? (el = v, false) : true;
            });

            return el;
        },
        getPolygonByNode: function getPolygonByNode(node) {
            var el = null;

            this.polygons.forEach(function (v) {
                return v.node === node ? (el = v, false) : true;
            });

            return el;
        },
        getElementByNode: function getElementByNode(node) {
            return !node.raphael ? null : this.getAnchorByNode(node) || this.getPolygonByNode(node);
        },
        removePolygon: function removePolygon(polygon) {
            polygon = polygon || this.activePolygon;

            var polygons = this.polygons,
                len = polygons.length;

            while (len--) {
                if (polygon === polygons[len]) {
                    polygon.remove();
                    this.activePolygon = null;
                    this.polygons.splice(len, 1);
                    this.blur().pending();
                    break;
                }
            }

            return this;
        },
        isLineCross: function isLineCross(points) {
            var act = this.activePolygon,
                flag = false;

            this.each(function () {
                if (this === act) {
                    return true;
                }

                if (overlap.isLineCross(points, this)) {
                    flag = true;
                    return false;
                }
            });

            return flag;
        },
        isBoxCross: function isBoxCross(p1, p2) {
            var act = this.activePolygon,
                polygons = this.polygons,
                len = polygons.length,
                flag = false,
                polygon = void 0,
                box = {};

            box.x = p1[0];
            box.y = p1[1];
            box.width = p2[0] - box.x;
            box.height = p2[1] - box.y;

            while (len--) {
                polygon = polygons[len];

                if (polygon === act) {
                    continue;
                }

                if (overlap.isBoxCross(box, polygon)) {
                    flag = true;
                    break;
                }
            }

            return flag;
        },
        isPolygonCross: function isPolygonCross() {
            if (!this.activePolygon) {
                return false;
            }

            var act = this.activePolygon,
                points = act.points.slice(0);

            points.push(points[0]);
            return this.isLineCross(points);
        },
        anchor: function anchor(x, y) {
            var anchor = this.brush.anchor(x, y);

            this.anchors.push(anchor);
            return anchor;
        },
        polygon: function polygon(points, close) {
            var polygon = this.brush.polygon(points, close),
                len = this.polygons.length;

            if (len) {
                polygon.insertBefore(this.polygons[len - 1]);
            } else if (this.anchors.length) {
                polygon.insertBefore(this.anchors[0]);
            }

            this.polygons.push(polygon);
            this.activePolygon = polygon;

            return polygon;
        },
        style: function style(_style, polygon) {
            polygon = polygon || this.activePolygon;

            if (!polygon) {
                return this;
            }

            if (polygon.style) {
                var o = polygon.style = {},
                    key;

                for (key in _style) {
                    if (_style.hasOwnProperty(key)) {
                        o[key] = _style[key];
                    }
                }
            } else {
                polygon.style = _style;
            }

            polygon.attr(_style);
            return this.focus(polygon);
        },
        fill: function fill(color, polygon) {
            return this.style({ fill: color }, polygon);
        },
        stroke: function stroke(color, polygon) {
            return this.style({ stroke: color }, polygon);
        },
        each: function each(handler) {
            var polygons = this.polygons,
                len = polygons.length,
                polygon,
                points;

            while (len--) {
                polygon = polygons[len];
                points = polygon.points;

                if (!points || points.length < 3) {
                    polygon.remove();
                    polygons.splice(len, 1);
                }

                if (handler.call(polygon, points, polygon) === false) {
                    return this;
                }
            }

            return this;
        },
        pending: function pending(state) {
            var _this = this;

            if (!state || this.state === state) {
                this.state = 'rejected';
                setTimeout(function () {
                    _this.state = 'pending';
                }, 300);
            }

            return this;
        },
        on: function on(name, callback) {
            this.events.on(name, callback);
            return this;
        },
        adopt: function adopt(brush) {
            this.events.on(brush(this));
            return this;
        }
    };

    /*
     *************************
     * 抛出接口
     *************************
     */

    module.exports = Paper;

/***/ },
/* 1 */
/***/ function(module, exports, __webpack_require__) {

    'use strict';

    /*
     *************************
     * 引入【Raphael】模块
     *************************
     */

    var Raphael = __webpack_require__(2),
        anchorStyle = {
        'fill': 'red',
        'stroke': 'none'
    },
        polygonStyle = {
        'fill': 'black',
        'fill-opacity': 0.5,
        'stroke': 'red'
    },
        ondraw = {};

    /*
     *************************
     * 扩展【draw】方法
     *************************
     */

    Raphael.el.draw = function () {
        var args = [].slice.call(arguments);

        if (this.utype && this.utype in ondraw) {
            return ondraw[this.utype].apply(this, args);
        }

        return this;
    };

    /*
     *************************
     * 扩展【anchor】图形
     *************************
     */

    // 创建【anchor】图形
    Raphael.fn.anchor = function (x, y) {
        if (Raphael.is(x, 'array')) {
            return this.anchor(x[0], x[1]);
        } else if (Raphael.is(x, 'object')) {
            return this.anchor(x.x, x.y);
        }

        var anchor = this.rect(x - 4, y - 4, 8, 8);

        anchor.point = [x, y];
        anchor.utype = 'anchor';
        return anchor.attr(anchorStyle);
    };

    // 重绘【anchor】图形
    ondraw.anchor = function (x, y) {

        if (Raphael.is(x, 'array')) {
            return this.draw(x[0], x[1]);
        } else if (Raphael.is(x, 'object')) {
            return this.draw(x.x, x.y);
        }

        this.point = [x, y];
        return this.attr({ x: x - 4, y: y - 4 });
    };

    /*
     *************************
     * 扩展【polygon】图形
     *************************
     */

    // 创建【polygon】图形
    Raphael.fn.polygon = function (points, z) {
        var path = polygonString(points, z),
            polygon = this.path(path);

        polygon.utype = 'polygon';
        polygon.points = points;
        polygon.close = z !== false;
        return polygon.attr(polygonStyle);
    };

    // 重绘【polygon】图形
    ondraw.polygon = function (points, z) {
        var path = polygonString(points, z);

        this.points = points;
        this.close = z !== false;
        return this.attr('path', path);
    };

    // 生成【polygonString】
    function polygonString(points, z) {
        if (points.length < 2) {
            return '';
        }

        var path = [],
            len = points.length,
            i = 0;

        for (; i < len; i++) {
            path.push(points[i].join());
        }

        path = 'M' + path[0] + 'L' + path.slice(1).join();
        return z === false ? path : path + 'Z';
    }

    /*
     *************************
     * 抛出接口
     *************************
     */
    module.exports = Raphael;

/***/ },
/* 2 */
/***/ function(module, exports) {

    module.exports = __WEBPACK_EXTERNAL_MODULE_2__;

/***/ },
/* 3 */
/***/ function(module, exports, __webpack_require__) {

    'use strict';

    /*
     *************************
     * 引入模块
     *************************
     */

    var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; };

    var eve = __webpack_require__(4);

    /*
     *************************
     * 定义【Events】类
     *************************
     */

    function Events(el) {
        var mouse = {},
            rect = void 0;

        // 监听鼠标按下事件
        el.onmousedown = function () {
            var e = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : event;


            // 获取当前鼠标属性
            rect = el.getBoundingClientRect();
            mouse.button = e.button;
            mouse.target = e.target || e.srcElement;
            mouse.x = mouse.sx = e.clientX - rect.left;
            mouse.y = mouse.sy = e.clientY - rect.top;

            // 结束上一次拖动
            mouse.down && eve('mouseup', el, mouse);
            mouse.down = true;
            eve('mousedown', el, mouse);

            el.focus();
            return false;
        };

        // 监听鼠标拖动事件
        el.onmousemove = function () {
            var e = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : event;

            if (!rect) {
                return false;
            }

            mouse.button = e.button;
            mouse.x = e.clientX - rect.left;
            mouse.y = e.clientY - rect.top;

            // 执行拖动或移动事件
            mouse.down && eve('drag', el, mouse);
            eve('mousemove', el, mouse);

            return false;
        };

        // 监听鼠标拖动结束事件
        el.onmouseup = function () {
            var e = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : event;


            // 获取鼠标信息
            mouse.button = e.button;
            mouse.x = e.clientX - rect.left;
            mouse.y = e.clientY - rect.top;
            mouse.down = false;

            eve('mouseup', el, mouse);

            return false;
        };

        // 监听鼠标离开事件
        el.onmouseleave = function () {
            var e = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : event;

            if (!rect) {
                return false;
            }

            // 获取位置信息
            mouse.button = e.button;
            mouse.x = e.clientX - rect.left;
            mouse.y = e.clientY - rect.top;

            // 执行鼠标弹起事件
            mouse.down && eve('mouseup', el, mouse);
            mouse.down = false;

            return false;
        };

        // 禁止右键菜单
        el.oncontextmenu = function () {
            var e = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : event;


            // 阻止默认事件
            e.returnValue = false;
            e.preventDefault && e.preventDefault();

            return false;
        };

        // 监听键盘事件
        el.setAttribute('tabindex', 0);
        el.onkeydown = function () {
            var e = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : event;

            var target = e.target || e.srcElement,
                code = e.keyCode || e.which;

            switch (code) {
                case 8:
                case 46:
                    return eve('remove', el, { target: target });
                case 13:
                    return eve('enter', el, { target: target });
                default:
                    break;
            }

            return false;
        };

        // 监听失去焦点事件
        el.style.outline = 'none';
        el.onblur = function () {
            var e = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : event;

            eve('blur', el, e);
        };

        // 禁止选择文字
        el.onselectstart = function () {
            return false;
        };
    }

    Events.prototype = {
        constructor: Events,
        on: function on(name, callback) {

            // 批量添加事件
            if ((typeof name === 'undefined' ? 'undefined' : _typeof(name)) === 'object') {
                for (var key in name) {
                    if (name.hasOwnProperty(key)) {
                        eve.on(key, name[key]);
                    }
                }

                return this;
            }

            eve.on(name, callback);
            return this;
        },
        off: function off(name, callback) {

            // 批量添加事件
            if ((typeof name === 'undefined' ? 'undefined' : _typeof(name)) === 'object') {
                for (var key in name) {
                    if (name.hasOwnProperty(key)) {
                        eve.unbind(key, name[key]);
                    }
                }

                return this;
            }

            eve.unbind(name, callback);
            return this;
        },
        emit: function emit(name, context, args) {
            eve(name, context, args);
            return this;
        }
    };

    /*
     *************************
     * 抛出接口
     *************************
     */

    module.exports = function (el) {
        return new Events(el);
    };

/***/ },
/* 4 */
/***/ function(module, exports) {

    module.exports = __WEBPACK_EXTERNAL_MODULE_4__;

/***/ },
/* 5 */
/***/ function(module, exports) {

    'use strict';

    /*
     *************************
     * 绘制矩形
     *************************
     */

    module.exports = function (paper) {
        var canvas = paper.canvas,
            el = null;

        return {
            mousedown: function mousedown(e) {
                if (paper.state === 'pending' && e.target === canvas) {
                    return el = e.target;
                }

                return el = null;
            },
            drag: function drag(e) {
                if (e.target !== el) {
                    return false;
                }

                var points = [[e.sx, e.sy], [e.x, e.sy], [e.x, e.y], [e.sx, e.y]];

                if (paper.state === 'pending') {

                    // 创建矩形
                    paper.state = 'dragdraw';
                    paper.blur().polygon(points);
                    paper.focus();
                } else if (paper.state === 'dragdraw') {

                    // 更新矩形
                    paper.activePolygon.draw(points);
                    paper.anchors.forEach(function (v, i) {
                        return v.draw(points[i]);
                    });
                }
            },
            mouseup: function mouseup(e) {

                if (paper.state !== 'dragdraw') {
                    return false;
                }

                if (Math.abs(e.x - e.sx) < 10 || Math.abs(e.y - e.sy) < 10) {

                    // 绘制区域太小时，取消绘制
                    paper.removePolygon();
                } else if (paper.isPolygonCross()) {
                    paper.events.emit('cross', paper, 'rect');

                    // 如果与其它图形相交，不作处理
                    paper.removePolygon();
                }

                // 更新状态
                el = null;
                paper.pending();
            }
        };
    };

/***/ },
/* 6 */
/***/ function(module, exports) {

    'use strict';

    /*
     *************************
     * 绘制多边形
     *************************
     */

    module.exports = function (paper) {
        var canvas = paper.canvas,
            data = [],
            finish = function finish() {
            var points = data.slice(0);

            if (points.length < 3) {
                return false;
            }

            points.push(points[0]);
            if (paper.isLineCross(points)) {

                // 触发交叉事件
                return paper.events.emit('cross', paper, 'polygon');
            } else {

                // 更新状态
                paper.activePolygon.draw(data);
                return paper.pending();
            }
        };

        return {
            mousedown: function mousedown(e) {

                // 开始绘画
                if (paper.state === 'pending') {
                    return e.target === canvas && paper.blur();
                }

                // 非【drawpath】下退出
                if (paper.state !== 'drawpath') {
                    return false;
                }

                if (e.button === 2) {
                    data.pop();
                    paper.anchors.pop().remove();

                    if (!data.length) {
                        return paper.removePolygon();
                    }

                    return false;
                }

                // 结束绘画
                if (e.target === paper.anchors[0].node) {
                    // 更新状态
                    return finish();
                }

                // 添加节点
                if (e.target === canvas || e.target === paper.activePolygon.node) {
                    var curr = [e.x, e.y];

                    data.push(curr);
                    return paper.anchor(curr);
                }
            },
            mousemove: function mousemove(e) {

                // 非【drawpath】时直接退出
                if (paper.state !== 'drawpath') {
                    return false;
                }

                var points = data.slice(0);

                // 绘制预览区域
                points.push([e.x, e.y]);
                paper.activePolygon.draw(points, false);
            },
            mouseup: function mouseup(e) {

                // 点击非画布时直接退出
                if (e.target !== canvas) {
                    return false;
                }

                // 在【pending】时绘制图形
                if (paper.state === 'pending') {
                    var curr = [e.x, e.y];

                    data = [curr];
                    paper.state = 'drawpath';
                    paper.polygon(data);
                    paper.anchor(curr);
                }
            },
            enter: function enter() {

                // 非【drawpath】下退出
                if (paper.state !== 'drawpath') {
                    return false;
                }

                // 更新状态
                return finish();
            },
            remove: function remove() {

                // 非【drawpath】时直接退出
                if (paper.state !== 'drawpath') {
                    return false;
                }

                return paper.removePolygon();
            }
        };
    };

/***/ },
/* 7 */
/***/ function(module, exports) {

    'use strict';

    /*
     *************************
     * 编辑多边形
     *************************
     */

    module.exports = function (paper) {
        var el = null,
            cachePoints = null,
            isMenuShow = void 0;

        return {
            mousedown: function mousedown(e) {

                // 隐藏右键菜单
                if (isMenuShow) {
                    isMenuShow = false;
                    paper.events.emit('hidemenu', el, e);
                }

                if (e.target === paper.canvas) {
                    return false;
                }

                if (paper.state !== 'pending') {
                    return false;
                }

                // 获取当前图形
                el = paper.getElementByNode(e.target);

                if (!el || !el.utype) {
                    return false;
                }

                switch (el.utype) {

                    // 激活当前多边形
                    case 'polygon':

                        // 选择多边形
                        el === paper.activePolygon || paper.focus(el);

                        // 触发右键事件
                        if (e.button === 2) {
                            isMenuShow = true;
                            paper.events.emit('showmenu', el, e);
                        }

                        return false;

                    // 开始编辑多边形
                    case 'anchor':
                        cachePoints = paper.activePolygon.points;
                        return paper.state = 'modify';

                    // 默认返回
                    default:
                        break;
                }
            },
            mousemove: function mousemove(e) {

                if (paper.state !== 'modify' || !el) {
                    return false;
                }

                var data = [];

                el.draw(e.x, e.y);
                paper.anchors.forEach(function (v) {
                    return data.push(v.point.slice(0));
                });
                paper.activePolygon.draw(data);
            },
            mouseup: function mouseup() {

                // 结束编辑
                if (paper.state === 'modify') {

                    if (paper.isPolygonCross()) {
                        paper.activePolygon.draw(cachePoints);
                        paper.focus().events.emit('cross', paper, 'modify');
                    }

                    return paper.pending();
                }
            },
            remove: function remove() {

                // 非【pending】下退出
                if (paper.state !== 'pending') {
                    return false;
                }

                if (paper.activePolygon) {
                    paper.removePolygon();
                }
            },
            blur: function blur(e) {

                // 隐藏右键菜单
                if (isMenuShow) {
                    isMenuShow = false;
                    paper.events.emit('hidemenu', el, e);
                }

                // 非【pending】下退出
                if (paper.state !== 'pending') {
                    return false;
                }

                paper.blur();
            }
        };
    };

/***/ },
/* 8 */
/***/ function(module, exports) {

    'use strict';

    function _isBoxCross(b1, b2) {
        var p1 = [b1.x, b1.y],
            p2 = [b1.x + b1.width, b1.y + b1.height],
            p3 = [b2.x, b2.y],
            p4 = [b2.x + b2.width, b2.y + b2.height];

        return onSegment(p1, p2, p3) || onSegment(p1, p2, p4);
    }

    function direction(p1, p2, p3) {
        var r1 = [p3[0] - p1[0], p3[1] - p1[1]],
            r2 = [p2[0] - p1[0], p2[1] - p1[1]];

        return r1[0] * r2[1] - r1[1] * r2[0];
    }

    function onSegment(p1, p2, p3) {
        var minX = void 0,
            minY = void 0,
            maxX = void 0,
            maxY = void 0,
            arrX = void 0,
            arrY = void 0;

        arrX = p1[0] < p2[0] ? [p1[0], p2[0]] : [p2[0], p1[0]];
        arrY = p1[1] < p2[1] ? [p1[1], p2[1]] : [p2[1], p1[1]];

        minX = arrX[0];
        maxX = arrX[1];
        minY = arrY[0];
        maxY = arrY[1];

        return p3[0] < minX || p3[0] > maxX || p3[1] < minY || p3[1] > maxY ? false : true;
    }

    function isLineHasCross(l1, l2) {
        var p1 = l1[0],
            p2 = l1[1],
            p3 = l2[0],
            p4 = l2[1],
            d1 = direction(p1, p2, p3),
            d2 = direction(p1, p2, p4),
            d3 = direction(p3, p4, p1),
            d4 = direction(p3, p4, p2);

        if (d1 * d2 < 0 && d3 * d4 < 0) {
            return true;
        } else if (d1 === 0 && onSegment(p1, p2, p3)) {
            return true;
        } else if (d2 === 0 && onSegment(p1, p2, p4)) {
            return true;
        } else if (d3 === 0 && onSegment(p3, p4, p1)) {
            return true;
        } else if (d4 === 0 && onSegment(p3, p4, p2)) {
            return true;
        } else {
            return false;
        }
    }

    function isLineCrossPolygon(line, points) {
        var len = points.length,
            i = 0;

        for (; i < len; i++) {
            if (isLineHasCross(line, [points[i], points[i + 1] || points[0]])) {
                return true;
            }
        }

        return false;
    }

    function isPointInPolygon(point, points) {
        var len = points.length,
            j = len - 1,
            i = 0,
            flag = false,
            x,
            p1,
            p2;

        for (; i < len; j = i++) {
            p1 = points[i];
            p2 = points[j];

            if ((p1[1] - point[1]) * (p2[1] - point[1]) > 0) {
                continue;
            }

            x = (point[1] - p1[1]) * (p2[0] - p1[0]) / (p2[1] - p1[1]) + p1[0];

            if (point[0] < x) {
                flag = !flag;
            }
        }

        return flag;
    }

    module.exports = {
        isLineCross: function isLineCross(points, polygon) {
            var len = points.length,
                i = 1,
                pp = polygon.points,
                flag = false,
                line = void 0;

            if (!pp || !pp.length) {
                return false;
            }

            for (; i < len; i++) {

                // 获取每条边的线段
                line = [points[i - 1], points[i]];

                // 判断线段是否与多边线相交
                if (isLineCrossPolygon(line, pp)) {
                    flag = true;
                    break;
                }
            }

            return flag || isPointInPolygon(pp[0], points.slice(0, -1));
        },
        isBoxCross: function isBoxCross(box, polygon) {

            // 判断区域盒子是否相交
            if (!_isBoxCross(box, polygon.getBBox())) {
                return false;
            }

            var points = polygon.points,
                len = points.length - 1,
                i = 0,
                flag = false,
                o = {};

            // 判断各边是否相交
            for (; i < len; i++) {

                // 获取每条边的线段
                o.x = points[i][0];
                o.y = points[i][1];
                o.width = points[i + 1][0] - o.x;
                o.height = points[i + 1][1] - o.y;

                // 判断线段是否与多边线相交
                if (_isBoxCross(box, o)) {
                    flag = true;
                    break;
                }
            }

            return flag;
        },
        isPolygonCross: function isPolygonCross(act, polygon) {

            // 判断区域盒子是否相交
            if (!_isBoxCross(act.getBBox(), polygon.getBBox())) {
                return false;
            }

            var start = polygon.points[0],
                points = act.points,
                len = points.length,
                i = 0,
                flag = false,
                line = void 0;

            // 判断【act】是否包含【polygon】
            if (act.isPointInside(start[0], start[1])) {
                return true;
            }

            // 判断各边是否相交
            for (; i < len; i++) {

                // 获取每条边的线段
                line = [points[i], points[i + 1] || points[0]];

                // 判断线段是否与多边线相交
                if (isLineCrossPolygon(line, polygon)) {
                    flag = true;
                    break;
                }
            }

            return flag;
        }
    };

/***/ }
/******/ ])
});
;
