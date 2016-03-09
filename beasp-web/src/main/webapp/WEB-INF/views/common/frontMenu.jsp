<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<div id="menu_wrap"
	class="yn jz container-fluid nav-bg m0 visible-lg visible-md visible-sm"
	style="position: relative; top: 255px;">
	<div class="container m0" style="position: relative;">
		<a href="<%=beaspPath %>/book/list/category/1" id="z1" class="nzz"> <span class="sort"><i
				class="fa fa-paint-brush"></i> &nbsp;文艺 <i class="fa fa-angle-down"></i></span>
		</a> | <a class="nzz" href="<%=beaspPath %>/book/list/category/6" id="z2">
			<span class="sort"><i class="fa fa-keyboard-o"></i> &nbsp;青春
				<i class="fa fa-angle-down"></i></span>
		</a> | <a class="nzz" href="<%=beaspPath %>/book/list/category/8" id="z3">
			<span class="sort"><i class="fa fa-film"></i> &nbsp;教育 <i
				class="fa fa-angle-down"></i></span>
		</a> | <a class="nzz" href="<%=beaspPath %>/book/list/category/11" id="z4">
			<span class="sort "><i class="fa fa-paper-plane-o"></i>
				&nbsp;生活 <i class="fa fa-angle-down"></i></span>
		</a> | <a class="nzz" href="<%=beaspPath %>/book/list/category/15" id="z5">
			<span class="sort"><i class="fa fa-dropbox"></i> &nbsp;人文社科 <i
				class="fa fa-angle-down"></i></span>
		</a> | <a class="nzz" href="<%=beaspPath %>/book/list/category/22" id="z6"> 
			<span class="sort"><i class="fa fa-code"></i> &nbsp;经管 <i
				class="fa fa-angle-down"></i></span>
		</a> | <a class="nzz" href="<%=beaspPath %>/book/list/category/26" id="z7">
			<span class="sort"><i class="fa fa-code"></i> &nbsp;科技 <i
				class="fa fa-angle-down"></i></span>
		</a>| <a class="nzz" href="<%=beaspPath %>/book/list/category/33" id="z8">
		 	<span class="sort"><i class="fa fa-code"></i> &nbsp;励志<i
				class="fa fa-angle-down"></i></span>
		</a>
	</div>

	<div class="container-fluid">
		<div id="n1" class="nav-zi ty" style="display: none; height: 244px;">
			<ul id="nz1" class="nn list-inline container m0 nadc"
				style="display: block;">
				<li><a href="<%=beaspPath %>/book/list/category/2"><i
						class="fa fa-delicious ls"></i> 文学</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/3"><i
						class="fa fa-bell-o ls"></i> 传记</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/4"><i
						class="fa fa-sort-numeric-desc ls"></i> 艺术</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/5"><i
						class="fa fa-comments-o ls"></i> 摄影</a></li>
			</ul>
			<ul id="nz2" class="nn list-inline container m0 nadc"
				style="display: none;">
				<li><a href="<%=beaspPath %>/book/list/category/7"><i
						class="fa fa-eyedropper ls"></i> 青春文学</a></li>
			</ul>
			<ul id="nz3" class="nn list-inline container m0 nadc"
				style="display: none;">
				<li><a href="<%=beaspPath %>/book/list/category/9"><i
						class="fa fa-play-circle-o ls"></i> 教材</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/10"><i
						class="fa fa-exchange ls"></i> 考试</a></li>
			</ul>
			<ul id="nz4" class="nn list-inline container m0 nadc"
				style="display: none;">
				<li><a href="<%=beaspPath %>/book/list/category/12"><i
						class="fa fa-long-arrow-right ls"></i> 休闲/爱好</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/13"><i
						class="fa fa-long-arrow-down ls"></i> 旅游/地图</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/14"><i
						class="fa fa-sitemap ls"></i> 体育/运动</a></li>
			</ul>
			<ul id="nz5" class="nn list-inline container m0 nadc"
				style="display: none;">
				<li><a href="<%=beaspPath %>/book/list/category/16"><i
						class="fa fa-rocket ls"></i> 历史</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/17"><i
						class="fa fa-expand ls"></i> 文化</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/18"><i
						class="fa fa-arrows-v ls"></i> 古籍</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/19"><i
						class="fa fa-cogs ls"></i> 心理学</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/20"><i
						class="fa fa-gift ls"></i> 社会学科</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/21"><i
						class="fa fa-gamepad ls"></i> 法律</a></li>
			</ul>
			<ul id="nz6" class="nn list-inline container m0 nadc"
				style="display: none;">
				<li><a href="<%=beaspPath %>/book/list/category/23"><i
						class="fa fa-rocket ls"></i> 管理</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/24"><i
						class="fa fa-expand ls"></i> 投资理财</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/25"><i
						class="fa fa-arrows-v ls"></i> 经济</a></li>
			</ul>
			<ul id="nz7" class="nn list-inline container m0 nadc"
				style="display: none;">
				<li><a href="<%=beaspPath %>/book/list/category/27"><i
						class="fa fa-rocket ls"></i> 科普读物</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/28"><i
						class="fa fa-expand ls"></i> 建筑</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/29"><i
						class="fa fa-arrows-v ls"></i> 医学</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/30"><i
						class="fa fa-cogs ls"></i> 计算机/网络</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/31"><i
						class="fa fa-gift ls"></i> 农业/林业</a></li>
				<li><a href="<%=beaspPath %>/book/list/category/32"><i
						class="fa fa-gamepad ls"></i> 自然科学</a></li>
			</ul>
			<ul id="nz8" class="nn list-inline container m0 nadc"
				style="display: none;">
				<li><a href="<%=beaspPath %>/book/list/category/34"><i
						class="fa fa-rocket ls"></i> 成功/励志</a></li>
			</ul>
		</div>

	</div>
</div>