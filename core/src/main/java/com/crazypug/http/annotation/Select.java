package com.crazypug.http.annotation;

import org.jsoup.select.Elements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <main role="main">
 * <!-- ======== START OF CLASS DATA ======== -->
 * <div class="header">
 * <div class="sub-title">
 * <span class="package-label-in-type"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">包</font></font></span>&nbsp;<a href="package-summary.html"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">org.jsoup.select</font></font></a>
 * </div>
 * <h1 title="类别选择器" class="title"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">类别选择器</font></font></h1>
 * </div>
 * <div class="inheritance" title="继承树"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * java.lang.Object
 * </font></font><div class="inheritance"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * org.jsoup.select.Selector
 * </font></font></div>
 * </div>
 * <section class="class-description" id="class-description">
 * <hr>
 * <div class="type-signature">
 * <span class="modifiers"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">公共类</font></font></span><span class="element-name"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">选择器</font></font></span> <span class="extends-implements"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">扩展对象</font></font></span>
 * </div>
 * <div class="block"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 类似 CSS 的元素选择器，用于查找与查询匹配的元素。
 * </font></font><h2><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">选择器语法</font></font></h2>
 * <p><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">选择器是一系列简单的选择器，由组合器分隔。</font><font style="vertical-align: inherit;">选择器</font></font><b><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">不区分大小写</font></font></b><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">（包括针对元素、属性和属性值）。</font></font></p>
 * <p><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">当没有提供元素选择器</font></font><code>*</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">时，通用选择器是隐式的（即</font></font><code>.header</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">和</font></font><code>*.header</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">是等效的）。</font></font></p>
 * <style>table.syntax tr td {vertical-align: top; padding-right: 2em; padding-top:0.5em; padding-bottom:0.5em; } table.syntax tr:hover{background-color: #eee;} table.syntax {border-spacing: 0px 0px;}</style>
 * <table summary="" class="syntax">
 * <colgroup>
 * <col span="1" style="width: 20%;">
 * <col span="1" style="width: 40%;">
 * <col span="1" style="width: 40%;">
 * </colgroup>
 * <tbody>
 * <tr>
 * <th align="left"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">图案</font></font></th>
 * <th align="left"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">匹配</font></font></th>
 * <th align="left"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">例子</font></font></th>
 * </tr>
 * <tr>
 * <td><code>*</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">任何元素</font></font></td>
 * <td><code>*</code></td>
 * </tr>
 * <tr>
 * <td><code>tag</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">具有给定标签名称的元素</font></font></td>
 * <td><code>div</code></td>
 * </tr>
 * <tr>
 * <td><code>*|E</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">任何命名空间（包括非命名空间）中类型 E 的元素</font></font></td>
 * <td><code>*|name</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">发现</font></font><code>&lt;fb:name&gt;</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">和</font></font><code>&lt;name&gt;</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">元素</font></font></td>
 * </tr>
 * <tr>
 * <td><code>ns|E</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">命名空间</font></font><i><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">ns中类型 E 的元素</font></font></i></td>
 * <td><code>fb|name</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">查找</font></font><code>&lt;fb:name&gt;</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">元素</font></font></td>
 * </tr>
 * <tr>
 * <td><code>#id</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">属性 ID 为“id”的元素</font></font></td>
 * <td><code>div#wrap</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">,</font></font><code>#logo</code></td>
 * </tr>
 * <tr>
 * <td><code>.class</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">类名为“class”的元素</font></font></td>
 * <td><code>div.left</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">,</font></font><code>.result</code></td>
 * </tr>
 * <tr>
 * <td><code>[attr]</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">具有名为“attr”的属性的元素（具有任何值）</font></font></td>
 * <td><code>a[href]</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">,</font></font><code>[title]</code></td>
 * </tr>
 * <tr>
 * <td><code>[^attrPrefix]</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">属性名称以“attrPrefix”开头的元素。</font><font style="vertical-align: inherit;">用于查找具有 HTML5 数据集的元素</font></font></td>
 * <td><code>[^data-]</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">,</font></font><code>div[^data-]</code></td>
 * </tr>
 * <tr>
 * <td><code>[attr=val]</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">属性名为“attr”且值等于“val”的元素</font></font></td>
 * <td><code>img[width=500]</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">,</font></font><code>a[rel=nofollow]</code></td>
 * </tr>
 * <tr>
 * <td><code>[attr="val"]</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">属性名为“attr”且值等于“val”的元素</font></font></td>
 * <td><code>span[hello="Cleveland"][goodbye="Columbus"]</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">,</font></font><code>a[rel="nofollow"]</code></td>
 * </tr>
 * <tr>
 * <td><code>[attr^=valPrefix]</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">具有名为“attr”的属性且值以“valPrefix”开头的元素</font></font></td>
 * <td><code>a[href^=http:]</code></td>
 * </tr>
 * <tr>
 * <td><code>[attr$=valSuffix]</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">具有名为“attr”的属性且值以“valSuffix”结尾的元素</font></font></td>
 * <td><code>img[src$=.png]</code></td>
 * </tr>
 * <tr>
 * <td><code>[attr*=valContaining]</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">具有名为“attr”的属性且值包含“valContaining”的元素</font></font></td>
 * <td><code>a[href*=/search/]</code></td>
 * </tr>
 * <tr>
 * <td><code>[attr~=<em>regex</em>]</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">具有名为“attr”的属性且值与正则表达式匹配的元素</font></font></td>
 * <td><code>img[src~=(?i)\\.(png|jpe?g)]</code></td>
 * </tr>
 * <tr>
 * <td></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">以上内容可以按任意顺序组合</font></font></td>
 * <td><code>div.header[title]</code></td>
 * </tr>
 * <tr>
 * <td colspan="3">
 * <h3><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">组合器</font></font></h3></td>
 * </tr>
 * <tr>
 * <td><code>E F</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">F 元素是 E 元素的后代</font></font></td>
 * <td><code>div a</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">,</font></font><code>.logo h1</code></td>
 * </tr>
 * <tr>
 * <td><code>E &gt; F</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">E 的 F 直系子代</font></font></td>
 * <td><code>ol &gt; li</code></td>
 * </tr>
 * <tr>
 * <td><code>E + F</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">F 元素紧跟在同级 E 之前</font></font></td>
 * <td><code>li + li</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">,</font></font><code>div.head + div</code></td>
 * </tr>
 * <tr>
 * <td><code>E ~ F</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">F 元素前面有同级 E</font></font></td>
 * <td><code>h1 ~ p</code></td>
 * </tr>
 * <tr>
 * <td><code>E, F, G</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">所有匹配元素 E、F 或 G</font></font></td>
 * <td><code>a[href], div, h3</code></td>
 * </tr>
 * <tr>
 * <td colspan="3">
 * <h3><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">伪选择器</font></font></h3></td>
 * </tr>
 * <tr>
 * <td><code>:lt(<em>n</em>)</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">兄弟索引小于</font></font><em><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">n的元素</font></font></em></td>
 * <td><code>td:lt(3)</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">查找每行的前 3 个单元格</font></font></td>
 * </tr>
 * <tr>
 * <td><code>:gt(<em>n</em>)</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">兄弟索引大于</font></font><em><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">n的元素</font></font></em></td>
 * <td><code>td:gt(1)</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">跳过前两个后查找单元格</font></font></td>
 * </tr>
 * <tr>
 * <td><code>:eq(<em>n</em>)</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">兄弟索引等于</font></font><em><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">n的元素</font></font></em></td>
 * <td><code>td:eq(0)</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">找到每行的第一个单元格</font></font></td>
 * </tr>
 * <tr>
 * <td><code>:has(<em>selector</em>)</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">至少包含一个与</font></font><em><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">选择器匹配的元素的元素</font></font></em></td>
 * <td><code>div:has(p)</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">查找</font></font><code>div</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">包含</font></font><code>p</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">元素的 s。</font></font><br><code>div:has(&gt; a)</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">选择</font></font><code>div</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">至少具有一个直接子</font></font><code>a</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">元素的元素。</font></font><br><code>section:has(h1, h2)</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">查找</font></font><code>section</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">包含 a</font></font><code>h1</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">或 a</font></font><code>h2</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">元素的元素</font></font></td>
 * </tr>
 * <tr>
 * <td><code>:is(<em>selector list</em>)</code></td>
 * <td><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">与选择器列表中的任何选择器匹配的元素</font></font></td>
 * <td><code>:is(h1, h2, h3, h4, h5, h6)</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">查找任何标题元素。</font></font><br><code>:is(section, article) &gt; :is(h1, h2)</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">查找 a</font></font><code>h1</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">或 ，它是 a</font><font style="vertical-align: inherit;">或 an</font></font><code>h2</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">的直接子代</font></font><code>section</code><font style="vertical-align: inherit;"></font><code>article</code></td>
 * </tr>
 * <tr>
 * <td><code>:not(<em>selector</em>)</code></td>
 * <td><font style="vertical-align: inherit;"></font><em><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">与选择器</font></font></em><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">不匹配的元素</font><font style="vertical-align: inherit;">。</font><font style="vertical-align: inherit;">也可以看看</font></font><a href="Elements.html#not(java.lang.String)"><code>Elements.not(String)</code></a></td>
 * <td><code>div:not(.logo)</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">查找所有不具有“logo”类的 div。
 * </font></font><p><code>div:not(:has(div))</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">查找不包含 div 的 div。</font></font></p></td>
 * </tr>
 * <tr>
 * <td><code>:contains(<em>text</em>)</code></td>
 * <td>elements that contains the specified text. The search is case insensitive. The text may appear in the found element, or any of its descendants. The text is whitespace normalized.
 * <p>To find content that includes parentheses, escape those with a <code>\</code>.</p></td>
 * <td><code>p:contains(jsoup)</code> finds p elements containing the text "jsoup".
 * <p><code>p:contains(hello \(there\) finds p elements containing the text "Hello (There)"</code></p></td>
 * </tr>
 * <tr>
 * <td><code>:containsOwn(<em>text</em>)</code></td>
 * <td>elements that directly contain the specified text. The search is case insensitive. The text must appear in the found element, not any of its descendants.</td>
 * <td><code>p:containsOwn(jsoup)</code> finds p elements with own text "jsoup".</td>
 * </tr>
 * <tr>
 * <td><code>:containsData(<em>data</em>)</code></td>
 * <td>elements that contains the specified <em>data</em>. The contents of <code>script</code> and <code>style</code> elements, and <code>comment</code> nodes (etc) are considered data nodes, not text nodes. The search is case insensitive. The data may appear in the found element, or any of its descendants.</td>
 * <td><code>script:contains(jsoup)</code> finds script elements containing the data "jsoup".</td>
 * </tr>
 * <tr>
 * <td><code>:containsWholeText(<em>text</em>)</code></td>
 * <td>elements that contains the specified <b>non-normalized</b> text. The search is case sensitive, and will match exactly against spaces and newlines found in the original input. The text may appear in the found element, or any of its descendants.
 * <p>To find content that includes parentheses, escape those with a <code>\</code>.</p></td>
 * <td><code>p:containsWholeText(jsoup\nThe Java HTML Parser)</code> finds p elements containing the text <code>"jsoup\nThe Java HTML Parser"</code> (and not other variations of whitespace or casing, as <code>:contains()</code> would. Note that <code>br</code> elements are presented as a newline.
 * <p></p></td>
 * </tr>
 * <tr>
 * <td><code>:containsWholeOwnText(<em>text</em>)</code></td>
 * <td>elements that <b>directly</b> contain the specified <b>non-normalized</b> text. The search is case sensitive, and will match exactly against spaces and newlines found in the original input. The text may appear in the found element, but not in its descendants.
 * <p>To find content that includes parentheses, escape those with a <code>\</code>.</p></td>
 * <td><code>p:containsWholeOwnText(jsoup\nThe Java HTML Parser)</code> finds p elements directly containing the text <code>"jsoup\nThe Java HTML Parser"</code> (and not other variations of whitespace or casing, as <code>:contains()</code> would. Note that <code>br</code> elements are presented as a newline.
 * <p></p></td>
 * </tr>
 * <tr>
 * <td><code>:matches(<em>regex</em>)</code></td>
 * <td>elements containing <b>whitespace normalized</b> text that matches the specified regular expression. The text may appear in the found element, or any of its descendants.</td>
 * <td><code>td:matches(\\d+)</code> finds table cells containing digits. <code>div:matches((?i)login)</code> finds divs containing the text, case insensitively.</td>
 * </tr>
 * <tr>
 * <td><code>:matchesWholeText(<em>regex</em>)</code></td>
 * <td>elements containing <b>non-normalized</b> whole text that matches the specified regular expression. The text may appear in the found element, or any of its descendants.</td>
 * <td><code>td:matchesWholeText(\\s{2,})</code> finds table cells a run of at least two space characters.</td>
 * </tr>
 * <tr>
 * <td><code>:matchesWholeOwnText(<em>regex</em>)</code></td>
 * <td>elements whose own <b>non-normalized</b> whole text matches the specified regular expression. The text must appear in the found element, not any of its descendants.</td>
 * <td><code>td:matchesWholeOwnText(\n\\d+)</code> finds table cells directly containing digits following a neewline.</td>
 * </tr>
 * <tr>
 * <td></td>
 * <td>The above may be combined in any order and with other selectors</td>
 * <td><code>.light:contains(name):eq(0)</code></td>
 * </tr>
 * <tr>
 * <td><code>:matchText</code></td>
 * <td>treats text nodes as elements, and so allows you to match against and select text nodes.
 * <p><b>Note</b> that using this selector will modify the DOM, so you may want to <code>clone</code> your document before using.</p></td>
 * <td><code>p:matchText:firstChild</code> with input <code>&lt;p&gt;One&lt;br /&gt;Two&lt;/p&gt;</code> will return one <a href="../nodes/PseudoTextElement.html" title="org.jsoup.nodes 中的类"><code>PseudoTextElement</code></a> with text "<code>One</code>".</td>
 * </tr>
 * <tr>
 * <td colspan="3">
 * <h3>Structural pseudo selectors</h3></td>
 * </tr>
 * <tr>
 * <td><code>:root</code></td>
 * <td>The element that is the root of the document. In HTML, this is the <code>html</code> element</td>
 * <td><code>:root</code></td>
 * </tr>
 * <tr>
 * <td><code>:nth-child(<em>a</em>n+<em>b</em>)</code></td>
 * <td>
 * <p>elements that have <code><em>a</em>n+<em>b</em>-1</code> siblings <b>before</b> it in the document tree, for any positive integer or zero value of <code>n</code>, and has a parent element. For values of <code>a</code> and <code>b</code> greater than zero, this effectively divides the element's children into groups of a elements (the last group taking the remainder), and selecting the <em>b</em>th element of each group. For example, this allows the selectors to address every other row in a table, and could be used to alternate the color of paragraph text in a cycle of four. The <code>a</code> and <code>b</code> values must be integers (positive, negative, or zero). The index of the first child of an element is 1.</p> Additionally, <code>:nth-child()</code> supports <code>odd</code> and <code>even</code> as arguments. <code>odd</code> is the same as <code>2n+1</code>, and <code>even</code> ss the same as <code>2n</code>.</td>
 * <td><code>tr:nth-child(2n+1)</code> finds every odd row of a table. <code>:nth-child(10n-1)</code> the 9th, 19th, 29th, etc, element. <code>li:nth-child(5)</code> the 5h li</td>
 * </tr>
 * <tr>
 * <td><code>:nth-last-child(<em>a</em>n+<em>b</em>)</code></td>
 * <td>elements that have <code><em>a</em>n+<em>b</em>-1</code> siblings <b>after</b> it in the document tree. Otherwise like <code>:nth-child()</code></td>
 * <td><code>tr:nth-last-child(-n+2)</code> the last two rows of a table</td>
 * </tr>
 * <tr>
 * <td><code>:nth-of-type(<em>a</em>n+<em>b</em>)</code></td>
 * <td>pseudo-class notation represents an element that has <code><em>a</em>n+<em>b</em>-1</code> siblings with the same expanded element name <em>before</em> it in the document tree, for any zero or positive integer value of n, and has a parent element</td>
 * <td><code>img:nth-of-type(2n+1)</code></td>
 * </tr>
 * <tr>
 * <td><code>:nth-last-of-type(<em>a</em>n+<em>b</em>)</code></td>
 * <td>pseudo-class notation represents an element that has <code><em>a</em>n+<em>b</em>-1</code> siblings with the same expanded element name <em>after</em> it in the document tree, for any zero or positive integer value of n, and has a parent element</td>
 * <td><code>img:nth-last-of-type(2n+1)</code></td>
 * </tr>
 * <tr>
 * <td><code>:first-child</code></td>
 * <td>elements that are the first child of some other element.</td>
 * <td><code>div &gt; p:first-child</code></td>
 * </tr>
 * <tr>
 * <td><code>:last-child</code></td>
 * <td>elements that are the last child of some other element.</td>
 * <td><code>ol &gt; li:last-child</code></td>
 * </tr>
 * <tr>
 * <td><code>:first-of-type</code></td>
 * <td>elements that are the first sibling of its type in the list of children of its parent element</td>
 * <td><code>dl dt:first-of-type</code></td>
 * </tr>
 * <tr>
 * <td><code>:last-of-type</code></td>
 * <td>elements that are the last sibling of its type in the list of children of its parent element</td>
 * <td><code>tr &gt; td:last-of-type</code></td>
 * </tr>
 * <tr>
 * <td><code>:only-child</code></td>
 * <td>elements that have a parent element and whose parent element have no other element children</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td><code>:only-of-type</code></td>
 * <td>an element that has a parent element and whose parent element has no other element children with the same expanded element name</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td><code>:empty</code></td>
 * <td>elements that have no children at all</td>
 * <td></td>
 * </tr>
 * </tbody>
 * </table>
 * <p>A word on using regular expressions in these selectors: depending on the content of the regex, you will need to quote the pattern using <b><code>Pattern.quote("regex")</code></b> for it to parse correctly through both the selector parser and the regex parser. E.g. <code>String query = "div:matches(" + Pattern.quote(regex) + ");"</code>.</p>
 * <p><b><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">转义特殊字符：</font></font></b><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">要匹配标签、ID 或其他不遵循常规 CSS 语法的选择器，必须使用该字符对查询进行转义</font></font><code>\</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">。</font><font style="vertical-align: inherit;">例如，要按 ID 进行匹配</font></font><code>&lt;p id="i.d"&gt;</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">，请使用</font></font><code>document.select("#i\\.d")</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">.</font></font></p>
 * </div>
 * <dl class="notes">
 * <dt><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 也可以看看：
 * </font></font></dt>
 * <dd>
 * <ul class="see-list-long">
 * <li><a href="../nodes/Element.html#select(java.lang.String)"><code>Element.select(String css)</code></a></li>
 * <li><a href="Elements.html#select(java.lang.String)"><code>Elements.select(String css)</code></a></li>
 * <li><a href="../nodes/Element.html#selectXpath(java.lang.String)"><code>Element.selectXpath(String xpath)</code></a></li>
 * </ul>
 * </dd>
 * </dl>
 * </section>
 * <section class="summary">
 * <ul class="summary-list">
 * <!-- ======== NESTED CLASS SUMMARY ======== -->
 * <li>
 * <section class="nested-class-summary" id="nested-class-summary">
 * <h2><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">嵌套类摘要</font></font></h2>
 * <div class="caption">
 * <span><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">嵌套类</font></font></span>
 * </div>
 * <div class="summary-table three-column-summary">
 * <div class="table-header col-first"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 修饰符和类型
 * </font></font></div>
 * <div class="table-header col-second"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 班级
 * </font></font></div>
 * <div class="table-header col-last"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 描述
 * </font></font></div>
 * <div class="col-first even-row-color">
 * <code>static class&nbsp;</code>
 * </div>
 * <div class="col-second even-row-color">
 * <code><a href="Selector.SelectorParseException.html" class="type-name-link" title="class in org.jsoup.select">Selector.SelectorParseException</a></code>
 * </div>
 * <div class="col-last even-row-color">
 * &nbsp;
 * </div>
 * </div>
 * </section></li><!-- ========== METHOD SUMMARY =========== -->
 * <li>
 * <section class="method-summary" id="method-summary">
 * <h2><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">方法总结</font></font></h2>
 * <div id="method-summary-table">
 * <div id="method-summary-table.tabpanel" role="tabpanel">
 * <div class="summary-table three-column-summary" aria-labelledby="method-summary-table-tab0">
 * <div class="table-header col-first"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 修饰符和类型
 * </font></font></div>
 * <div class="table-header col-second"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 方法
 * </font></font></div>
 * <div class="table-header col-last"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 描述
 * </font></font></div>
 * <div class="col-first even-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4">
 * <code>static <a href="Elements.html" title="class in org.jsoup.select">Elements</a></code>
 * </div>
 * <div class="col-second even-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4">
 * <code><a href="#select(java.lang.String,java.lang.Iterable)" class="member-name-link">select</a><wbr>(String&nbsp;query, Iterable&lt;<a href="../nodes/Element.html" title="class in org.jsoup.nodes">Element</a>&gt;&nbsp;roots)</code>
 * </div>
 * <div class="col-last even-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4">
 * <div class="block"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 查找与选择器匹配的元素。
 * </font></font></div>
 * </div>
 * <div class="col-first odd-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4">
 * <code>static <a href="Elements.html" title="class in org.jsoup.select">Elements</a></code>
 * </div>
 * <div class="col-second odd-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4">
 * <code><a href="#select(java.lang.String,org.jsoup.nodes.Element)" class="member-name-link">select</a><wbr>(String&nbsp;query, <a href="../nodes/Element.html" title="class in org.jsoup.nodes">Element</a>&nbsp;root)</code>
 * </div>
 * <div class="col-last odd-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4">
 * <div class="block"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 查找与选择器匹配的元素。
 * </font></font></div>
 * </div>
 * <div class="col-first even-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4">
 * <code>static <a href="Elements.html" title="class in org.jsoup.select">Elements</a></code>
 * </div>
 * <div class="col-second even-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4">
 * <code><a href="#select(org.jsoup.select.Evaluator,org.jsoup.nodes.Element)" class="member-name-link">select</a><wbr>(<a href="Evaluator.html" title="class in org.jsoup.select">Evaluator</a>&nbsp;evaluator, <a href="../nodes/Element.html" title="class in org.jsoup.nodes">Element</a>&nbsp;root)</code>
 * </div>
 * <div class="col-last even-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4">
 * <div class="block"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 查找与选择器匹配的元素。
 * </font></font></div>
 * </div>
 * <div class="col-first odd-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4">
 * <code>static @Nullable <a href="../nodes/Element.html" title="class in org.jsoup.nodes">Element</a></code>
 * </div>
 * <div class="col-second odd-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4">
 * <code><a href="#selectFirst(java.lang.String,org.jsoup.nodes.Element)" class="member-name-link">selectFirst</a><wbr>(String&nbsp;cssQuery, <a href="../nodes/Element.html" title="class in org.jsoup.nodes">Element</a>&nbsp;root)</code>
 * </div>
 * <div class="col-last odd-row-color method-summary-table method-summary-table-tab1 method-summary-table-tab4">
 * <div class="block"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 查找与查询匹配的第一个元素。
 * </font></font></div>
 * </div>
 * </div>
 * </div>
 * </div>
 * <div class="inherited-list">
 * <h3 id="methods-inherited-from-class-java.lang.Object"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">从类 java.lang.Object 继承的方法</font></font></h3><code>clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait, wait, wait</code>
 * </div>
 * </section></li>
 * </ul>
 * </section>
 * <section class="details">
 * <ul class="details-list">
 * <!-- ============ METHOD DETAIL ========== -->
 * <li>
 * <section class="method-details" id="method-detail">
 * <h2><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">方法详情</font></font></h2>
 * <ul class="member-list">
 * <li>
 * <section class="detail" id="select(java.lang.String,org.jsoup.nodes.Element)">
 * <h3><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">选择</font></font><span class="source-link"><a href="https://github.com/jhy/jsoup/blob/master/src/main/java/org/jsoup/select/Selector.java#L98"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">来源</font></font></a></span></h3>
 * <div class="member-signature">
 * <span class="modifiers"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">公共静态</font></font></span>&nbsp;<span class="return-type"><a href="Elements.html" title="org.jsoup.select 中的类"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">元素</font></font></a></span>&nbsp;<span class="element-name"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">选择</font></font></span><wbr><span class="parameters"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">（字符串查询，</font></font><a href="../nodes/Element.html" title="org.jsoup.nodes 中的类"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">元素</font></font></a><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">&nbsp;根）</font></font></span>
 * </div>
 * <div class="block"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 查找与选择器匹配的元素。
 * </font></font></div>
 * <dl class="notes">
 * <dt><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 参数：
 * </font></font></dt>
 * <dd>
 * <code>query</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">- CSS选择器
 * </font></font></dd>
 * <dd>
 * <code>root</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">- 下降到的根元素
 * </font></font></dd>
 * <dt><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 返回：
 * </font></font></dt>
 * <dd><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 匹配元素，如果没有则为空
 * </font></font></dd>
 * <dt><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 投掷：
 * </font></font></dt>
 * <dd>
 * <code><a href="Selector.SelectorParseException.html" title="class in org.jsoup.select">Selector.SelectorParseException</a></code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">-（未选中）无效的 CSS 查询。
 * </font></font></dd>
 * </dl>
 * </section></li>
 * <li>
 * <section class="detail" id="select(org.jsoup.select.Evaluator,org.jsoup.nodes.Element)">
 * <h3><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">选择</font></font><span class="source-link"><a href="https://github.com/jhy/jsoup/blob/master/src/main/java/org/jsoup/select/Selector.java#L110"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">来源</font></font></a></span></h3>
 * <div class="member-signature">
 * <span class="modifiers"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">公共静态</font></font></span>&nbsp;<span class="return-type"><a href="Elements.html" title="org.jsoup.select 中的类"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">元素</font></font></a></span>&nbsp;<span class="element-name"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">选择</font></font></span><wbr><span class="parameters"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">（</font></font><a href="Evaluator.html" title="org.jsoup.select 中的类"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">评估器</font></font></a><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">&nbsp;评估器，</font></font><a href="../nodes/Element.html" title="org.jsoup.nodes 中的类"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">元素</font></font></a><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">&nbsp;根）</font></font></span>
 * </div>
 * <div class="block"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 查找与选择器匹配的元素。
 * </font></font></div>
 * <dl class="notes">
 * <dt><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 参数：
 * </font></font></dt>
 * <dd>
 * <code>evaluator</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">- CSS选择器
 * </font></font></dd>
 * <dd>
 * <code>root</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">- 下降到的根元素
 * </font></font></dd>
 * <dt><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 返回：
 * </font></font></dt>
 * <dd><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 匹配元素，如果没有则为空
 * </font></font></dd>
 * </dl>
 * </section></li>
 * <li>
 * <section class="detail" id="select(java.lang.String,java.lang.Iterable)">
 * <h3><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">选择</font></font><span class="source-link"><a href="https://github.com/jhy/jsoup/blob/master/src/main/java/org/jsoup/select/Selector.java#L123"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">来源</font></font></a></span></h3>
 * <div class="member-signature">
 * <span class="modifiers"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">公共静态</font></font></span>&nbsp;<span class="return-type"><a href="Elements.html" title="org.jsoup.select 中的类"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">元素</font></font></a></span>&nbsp;<span class="element-name"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">选择</font></font></span><wbr><span class="parameters"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">（字符串查询，Iterable&lt;Element&gt;</font></font><a href="../nodes/Element.html" title="org.jsoup.nodes 中的类"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">根</font></font></a><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">）</font></font></span>
 * </div>
 * <div class="block"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 查找与选择器匹配的元素。
 * </font></font></div>
 * <dl class="notes">
 * <dt><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 参数：
 * </font></font></dt>
 * <dd>
 * <code>query</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">- CSS选择器
 * </font></font></dd>
 * <dd>
 * <code>roots</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">- 下降到的根元素
 * </font></font></dd>
 * <dt><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 返回：
 * </font></font></dt>
 * <dd><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 匹配元素，如果没有则为空
 * </font></font></dd>
 * </dl>
 * </section></li>
 * <li>
 * <section class="detail" id="selectFirst(java.lang.String,org.jsoup.nodes.Element)">
 * <h3><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">选择第一个</font></font><span class="source-link"><a href="https://github.com/jhy/jsoup/blob/master/src/main/java/org/jsoup/select/Selector.java#L165"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">来源</font></font></a></span></h3>
 * <div class="member-signature">
 * <span class="modifiers"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">公共静态</font></font></span>&nbsp;<span class="return-type"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">@Nullable </font></font><a href="../nodes/Element.html" title="org.jsoup.nodes 中的类"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">Element</font></font></a></span>&nbsp;<span class="element-name"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;"> selectFirst</font></font></span><wbr><span class="parameters"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">（字符串 cssQuery，</font></font><a href="../nodes/Element.html" title="org.jsoup.nodes 中的类"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">元素</font></font></a><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">&nbsp;根）</font></font></span>
 * </div>
 * <div class="block"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 查找与查询匹配的第一个元素。
 * </font></font></div>
 * <dl class="notes">
 * <dt><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 参数：
 * </font></font></dt>
 * <dd>
 * <code>cssQuery</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">- CSS选择器
 * </font></font></dd>
 * <dd>
 * <code>root</code><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">- 下降到的根元素
 * </font></font></dd>
 * <dt><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 返回：
 * </font></font></dt>
 * <dd><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
 * 匹配元素，如果没有则为</font></font><b><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">null</font></font></b><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">。
 * </font></font></dd>
 * </dl>
 * </section></li>
 * </ul>
 * </section></li>
 * </ul>
 * </section><!-- ========= END OF CLASS DATA ========= -->
 * </main>
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Select {

    String value() default "";

    SelectMethod method() default SelectMethod.ALL;

    enum SelectMethod {


        ALL(Elements.class), VAL(String.class), HTML(String.class);


        public Class getType() {
            return type;
        }

        Class type;

        SelectMethod(Class type) {
            this.type = type;
        }
    }


}