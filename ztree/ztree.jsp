<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/ztree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/ztree/js/jquery.ztree.exedit-3.2.js"></script>
<script type="text/javascript">
	var log, className = "dark";
	var zNodes=[{name:"添加一个父节点"}];
	var setting = {
		async : {
			enable : true,
			url : "department!getNode.action",
			autoParam : [ "name" ],
			dataFilter : filter
		},
		view : {
			addHoverDom : addHoverDom,
			removeHoverDom : removeHoverDom,
			selectedMulti : false
		},
		edit : {
			enable:true,
			removeTitle:"删除",
			renameTitle:"重命名",
			editNameSelectAll:true
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId",
				rootPId : ""
			}
		},
		callback : {
			beforeDrag : beforeDrag,
			beforeEditName : beforeEditName,
			beforeRemove : beforeRemove,
			beforeRename : beforeRename,
			onRemove : onRemove,
			onRename : onRename
		}
	};
	function beforeDrag(treeId, treeNodes) {
		return false;
	}
	var d;
	function beforeRemove(treeId, treeNode) {
		className = (className === "dark" ? "" : "dark");
		showLog("[ " + getTime() + " beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
				+ treeNode.name);
		var zTree = $.fn.zTree.getZTreeObj("tree");
		zTree.selectNode(treeNode);
		if (confirm("确认删除 节点 -- " + treeNode.name + " 吗？")) {
			$.ajax({
				url : "department!delNode.action?r=" + (new Date()).getTime(),
				data : {
					requestId : treeNode.id
				},
				dataType : "json",
				type : "POST",
				async : false, //需要同步操作，否则无法return
				success : function(data, status) {
					d = true;
					if (data.result == 0) {
						d = false;
						alert("删除失败，请先删除子节点");
					}
				},
				error : function(data, status) {
					alert(status);
				}
			});
		}
		return d;
	}
	function onRemove(e, treeId, treeNode) {
		showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
				+ treeNode.name);
	}
	function beforeRename(treeId, treeNode, newName) {
		className = (className === "dark" ? "" : "dark");
		showLog("[ " + getTime() + " beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; "
				+ treeNode.name);
		if (newName.length == 0) {
			alert("节点名称不能为空.");
			var zTree = $.fn.zTree.getZTreeObj("tree");
			setTimeout(function() {
				zTree.editName(treeNode)
			}, 10);
			return false;
		}
		return true;
	}

	function onRename(e, treeId, treeNode) {
		showLog("[ " + getTime() + " onRename ]&nbsp;&nbsp;&nbsp;&nbsp; "
				+ treeNode.name);
		$.post("department!editNode.action?r=" + (new Date()).getTime(), {
			requestId : treeNode.id,
			name : treeNode.name
		}, function(data, status) {
		});
	}
	function showLog(str) {
		if (!log)
			log = $("#log");
		log.append("<li class='"+className+"'>" + str + "</li>");
		if (log.children("li").length > 8) {
			log.get(0).removeChild(log.children("li")[0]);
		}
	}
	function getTime() {
		var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
				.getSeconds(), ms = now.getMilliseconds();
		return (h + ":" + m + ":" + s + " " + ms);
	}

	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.id).length > 0)
			return;
		var addStr = "<span class='button' style='margin-right:2px; background-position:-143px 0; vertical-align:top; *vertical-align:middle' id='addBtn_"
				+ treeNode.id + "' title='添加' onfocus='this.blur();'></span>";
		sObj.append(addStr);
		var btn = $("#addBtn_" + treeNode.id);
		if (btn)
			btn.bind("click", function() {
				var zTree = $.fn.zTree.getZTreeObj("tree");
				var newName = "新部门" + (newCount++);
				var children = zTree.addNodes(treeNode, {
					id : (100 + newCount),
					pId : treeNode.id,
					name : newName
				});
				/*newCount自增数,treeNode.id，新增的节点的父ID*/
				$.post("department!addNode.action?r=" + (new Date()).getTime(),
						{
							requestId : treeNode.id,
							name : newName
						}, function(data, status) {
							children[0].id = data.nodeId;
						});
				return false;
			});
	};
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.id).unbind().remove();
	};
	function selectAll() {
		var zTree = $.fn.zTree.getZTreeObj("tree");
		zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
	}
	function beforeEditName(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("tree");
		zTree.selectNode(treeNode);
		return true;
	}
	function filter(treeId, parentNode, childNodes) {
		if (!childNodes)
			return null;
		for ( var i = 0, l = childNodes.length; i < l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}
	$(document).ready(function() {
		$.fn.zTree.init($("#tree"), setting);
	});
</script>
<title>Insert title here</title>
</head>
<body>
	<ul id="tree" class="ztree" style="width: 260px; overflow: auto;"></ul>
</body>
</html>