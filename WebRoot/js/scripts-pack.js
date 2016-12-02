jQuery.cookie = function(h, m, i) {
	if (typeof m != "undefined") {
		i = i || {};
		if (m === null) {
			m = "";
			i.expires = -1
		}
		var k = "";
		if (i.expires
				&& (typeof i.expires == "number" || i.expires.toUTCString)) {
			if (typeof i.expires == "number") {
				k = new Date;
				k.setTime(k.getTime() + i.expires * 24 * 60 * 60 * 1000)
			} else
				k = i.expires;
			k = "; expires=" + k.toUTCString()
		}
		var l = i.path ? "; path=" + i.path : "", q = i.domain ? "; domain="
				+ i.domain : "";
		i = i.secure ? "; secure" : "";
		document.cookie = [ h, "=", encodeURIComponent(m), k, l, q, i ]
				.join("")
	} else {
		m = null;
		if (document.cookie && document.cookie != "") {
			i = document.cookie.split(";");
			for (k = 0; k < i.length; k++) {
				l = jQuery.trim(i[k]);
				if (l.substring(0, h.length + 1) == h + "=") {
					m = decodeURIComponent(l.substring(h.length + 1));
					break
				}
			}
		}
		return m
	}
};
(function(h) {
	h.fn.tableFilter = function(m) {
		var i, k, l, q, t, n, s, u, o, g = m || h.fn.tableFilter.defaults;
		if (!g.filterDelay)
			g.filterDelay = h.fn.tableFilter.defaults.filterDelay;
		if (!g.selectOptionLabel)
			g.selectOptionLabel = h.fn.tableFilter.defaults.selectOptionLabel;
		this.each(function() {
			l = h(this);
			D()
		});
		function D() {
			t = E() + "_filters";
			F();
			G();
			H()
		}
		function E() {
			return l.attr("id") || l.attr("name")
		}
		function G() {
			n.filter("input").keyup(r);
			n.filter("select").change(r);
			if (g.clearFiltersControls)
				for ( var a = 0; a < g.clearFiltersControls.length; a++)
					g.clearFiltersControls[a].click(function() {
						I();
						return false
					});
			if (g.additionalFilterTriggers)
				for (a = 0; a < g.additionalFilterTriggers.length; a++) {
					var b = g.additionalFilterTriggers[a];
					switch (b.attr("type")) {
					case "select-one":
						b.change(r);
						break;
					case "text":
						b.attr("title", h.fn.tableFilter.filterToolTipMessage);
						b.keyup(r);
						break;
					case "checkbox":
						b.click(r);
						break;
					default:
						throw "Filter type " + b.attr("type")
								+ " is not supported";
					}
				}
		}
		function I() {
			n.val("");
			if (g.additionalFilterTriggers)
				for ( var a = 0; a < g.additionalFilterTriggers.length; a++) {
					var b = g.additionalFilterTriggers[a];
					switch (b.attr("type")) {
					case "text":
						b.val("");
						break;
					case "checkbox":
						b.attr("checked", false);
						break;
					default:
						throw "Filter type " + b.attr("type")
								+ " is not supported";
					}
				}
			w()
		}
		function F() {
			u = l.find("thead tr:first th");
			o = l.find("tbody tr");
			J();
			n = l.find("thead tr:last").find("input,select");
			s = [];
			n.each(function() {
				s.push(K($(this)))
			})
		}
		function J() {
			for ( var a = $("<tr class='filters'></tr>"), b = 0; b < u.length; b++) {
				var c = $(u[b]), d = c.attr("filter") === "false" ? "" : c
						.text();
				if (d.length > 1) {
					d = $("<td/>");
					var e = $(L(b, c));
					e.width(c.width());
					d.append(e)
				} else
					d = $("<td>&nbsp;</td>");
				a.append(d)
			}
			l.find("thead").append(a)
		}
		function L(a, b) {
			var c = b.attr("filter-type");
			c || (c = "text");
			switch (c) {
			case "text":
				return "<input type='text' id='filter_" + a
						+ "' class='filter' title='"
						+ h.fn.tableFilter.filterToolTipMessage + "'/>";
			case "ddl":
				return M(a, b);
			default:
				throw "filter-type: " + c + " is not supported";
			}
		}
		function M(a) {
			var b = "<select id='filter_" + a
					+ "' class='filter'><option value=''>"
					+ g.selectOptionLabel + "</option>";
			a = o.find("td:nth-child(" + (a + 1) + ")");
			var c = [];
			h.each(a, function() {
				var d = $(this).text();
				if (!(!d || d === "&nbsp;")) {
					for ( var e = 0; e < c.length; e++)
						if (c[e] === d)
							return;
					c.push(d)
				}
			});
			c.sort();
			h.each(c, function() {
				b += '<option value="' + this.replace('"', "&#034;") + '">'
						+ this + "</option>"
			});
			b += "</select>";
			return b
		}
		function H() {
			if (h.cookie) {
				var a = h.cookie(t);
				if (a) {
					a = a.split(";");
					for ( var b = 0; b < a.length; b++) {
						var c = a[b].split(",");
						a[b] = {
							id : c[0],
							value : c[3],
							idx : c[1],
							type : c[2]
						}
					}
					x(a, true)
				}
			}
		}
		function K(a) {
			a = a.parent("td");
			return a = a.parent("tr").children().index(a)
		}
		function r() {
			i = (new Date).getTime();
			y()
		}
		function y() {
			k && clearTimeout(k);
			q = true;
			var a = (new Date).getTime();
			if (a - i >= g.filterDelay)
				w();
			else
				k = setTimeout(y, g.filterDelay / 3)
		}
		function w() {
			q = false;
			clearTimeout(k);
			var a = N();
			x(a, false);
			O(a)
		}
		function N() {
			for ( var a = [], b = 0; b < n.length; b++) {
				var c = z(h(n[b]));
				if (c)
					a[a.length] = c
			}
			if (!g.additionalFilterTriggers)
				return a;
			for (b = 0; b < g.additionalFilterTriggers.length; b++)
				if (c = z(g.additionalFilterTriggers[b]))
					a[a.length] = c;
			return a
		}
		function z(a) {
			var b = a.attr("type");
			switch (b) {
			case "text":
				b = a.val() === null ? null : a.val().toLowerCase();
				break;
			case "select-one":
				b = a.val() === null ? null : a.val();
				break;
			case "checkbox":
				b = a.attr("checked");
				break;
			default:
				throw "Filter type " + b + " is not supported";
			}
			if (b === null || b.length <= 0)
				return null;
			var c = P(a);
			return {
				id : a.attr("id"),
				value : b.toString(),
				idx : c,
				type : a.attr("type")
			}
		}
		function O(a) {
			if (h.cookie) {
				for ( var b = [], c = 0; c < a.length; c++) {
					b.length > 0 && b.push(";");
					var d = a[c];
					b.push(d.id);
					b.push(",");
					b.push(d.idx);
					b.push(",");
					b.push(d.type);
					b.push(",");
					b.push(d.value)
				}
				b = b.join("");
				h.cookie(t, b)
			}
		}
		function x(a, b) {
			g.filteringRows && g.filteringRows(a);
			Q(a, b);
			g.filteredRows && g.filteredRows(a)
		}
		function Q(a, b) {
			R();
			if (!((!a || a.length) === 0 && (g.matchingRow === null || g.matchingCell)))
				if (a === null || a.length === 0)
					A(null);
				else
					for ( var c = 0; c < a.length; c++) {
						var d = a[c];
						if (b && d.type && d.id)
							switch (d.type) {
							case "select-one":
							case "text":
								l.find("#" + d.id).val(d.value);
								break;
							case "checkbox":
								l.find("#" + d.id).attr("checked",
										d.value === "true");
								break;
							default:
								throw "Filter type " + d.type
										+ " is not supported";
							}
						A(d)
					}
			S()
		}
		function R() {
			o.removeAttr("filtermatch")
		}
		function A(a) {
			for ( var b = T(a), c = a === null ? -1 : a.idx, d = 0; d < o.length; d++) {
				if (q)
					return;
				var e = h(o[d]);
				e.attr("filtermatch") || U(a, e, b, c)
						|| e.attr("filtermatch", "false")
			}
		}
		function T(a) {
			if (a === null)
				return null;
			switch (a.type) {
			case "select-one":
				return [ a.value ];
			case "text":
				return V(a.value);
			case "checkbox":
				return null;
			default:
				throw "Filter type " + f.attr("type") + " is not supported";
			}
		}
		function S() {
			for ( var a = 0; a < o.length; a++) {
				if (q)
					return;
				var b = h(o[a]);
				b.attr("filtermatch") === "false" ? b.hide() : b.show()
			}
		}
		function P(a) {
			a = a.parent("td");
			if (!a || a.length <= 0)
				return -1;
			var b = a.parent();
			return b.children("td").index(a)
		}
		function U(a, b, c, d) {
			var e = b.children("td");
			if (d < 0) {
				for (d = 0; d < s.length; d++) {
					var j = h(e[s[d]]);
					if (B(a, j, c))
						return C(a, b, c)
				}
				return false
			} else
				return B(a, h(e[d]), c) && C(a, b, c)
		}
		function C(a, b, c) {
			if (!g.matchingRow)
				return true;
			return g.matchingRow(a, b, c)
		}
		function B(a, b, c) {
			var d = b.text();
			if (!W(d, c, a != null && a.type === "select-one"))
				return false;
			return !g.matchingCell || g.matchingCell(a, b, c)
		}
		var p;
		function V(a) {
			if (!a)
				return null;
			if (!p) {
				p = {};
				p.or = 1;
				p.and = 2;
				p.not = 3
			}
			a = a.toLowerCase();
			a = X(a);
			a = Y(a);
			a = Z(a);
			return a = a.split("|")
		}
		function X(a) {
			a = aa(a);
			for ( var b = [], c = 0; c < a.length; c++) {
				for ( var d = a[c], e = d.indexOf("("); e != -1;) {
					if (e > 0)
						b[b.length] = d.substring(0, e);
					b[b.length] = "(";
					d = d.substring(e + 1);
					e = d.indexOf("(")
				}
				for (e = d.indexOf(")"); e != -1;) {
					if (e > 0)
						b[b.length] = d.substring(0, e);
					b[b.length] = ")";
					d = d.substring(e + 1);
					e = d.indexOf(")")
				}
				if (d.length > 0)
					b[b.length] = d
			}
			return b
		}
		function aa(a) {
			var b = /([^"^\s]+)\s*|"([^"]+)"\s*/g;
			a = a.match(b);
			for (b = 0; b < a.length; b++)
				a[b] = ba(a[b]).replace(/"/g, "");
			return a
		}
		function Y(a) {
			for ( var b = [], c, d = 0; d < a.length; d++) {
				var e = a[d];
				if (!(!e || e.length === 0)) {
					if (e.indexOf("-") === 0) {
						e = "not";
						a[d] = a[d].substring(1);
						d--
					}
					if (c)
						if (c != "(" && c != "not" && c != "and" && c != "or"
								&& e != "and" && e != "or" && e != ")")
							b[b.length] = "and";
					c = b[b.length] = e
				}
			}
			return b
		}
		function Z(a) {
			for ( var b = "", c = [], d, e = 0; e < a.length; e++) {
				var j = a[e];
				if (j.length !== 0)
					if (j != "and" && j != "or" && j != "not" && j != "("
							&& j != ")")
						b = b + "|" + j;
					else if (c.length === 0 || j === "(")
						c.push(j);
					else if (j === ")")
						for (d = c.pop(); d != "(";) {
							b = b + "|" + d;
							d = c.pop()
						}
					else {
						if (c[c.length - 1] !== "(")
							for (; c.length != 0;) {
								if (c[c.length - 1] === "(")
									break;
								if (p[c[c.length - 1]] > p[j]) {
									d = c.pop();
									b = b + "|" + d
								} else
									break
							}
						c.push(j)
					}
			}
			for (; c.length > 0;)
				b = b + "|" + c.pop();
			return b.substring(1)
		}
		function ba(a) {
			return a.replace(/^\s\s*/, "").replace(/\s\s*$/, "")
		}
		function W(a, b, c) {
			if (!b)
				return true;
			a = c ? a : a.toLowerCase();
			for ( var d = [], e, j, v = 0; v < b.length; v++) {
				token = b[v];
				if (token != "and" && token != "or" && token != "not")
					d.push(c ? a === token : a.indexOf(token) >= 0);
				else if (token === "and") {
					e = d.pop();
					j = d.pop();
					d.push(e && j)
				} else if (token === "or") {
					e = d.pop();
					j = d.pop();
					d.push(e || j)
				} else if (token === "not") {
					e = d.pop();
					d.push(!e)
				}
			}
			return d.length === 1 && d.pop()
		}
	};
	h.fn.tableFilter.filterToolTipMessage = 'Quotes (") match phrases. (not) excludes a match from the results. (or) can be used to do Or searches. I.e. [red or blue] will match either red or blue.';
	h.fn.tableFilter.defaults = {
		additionalFilterTriggers : [],
		clearFiltersControls : [],
		matchingRow : null,
		matchingCell : null,
		filteringRows : null,
		filteredRows : null,
		filterDelay : 200,
		selectOptionLabel : "Select..."
	}
})(jQuery);