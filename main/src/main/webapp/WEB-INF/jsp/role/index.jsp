<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 角色维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <%@ include file="/WEB-INF/jsp/common/top.jsp"%>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <jsp:include page="/WEB-INF/jsp/common/menu.jsp"></jsp:include>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="queryContent" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button id="deleteBatchBtn" type="button" class="btn btn-danger"  style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="addBtn" type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${APP_PATH}/role/add.htm'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="allcheckbox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
<%--                                  <c:forEach items="${page.datas }" var="roler" varStatus="status">&ndash;%&gt;--%>
<%--                                    <tr>--%>
<%--                                        <td>${status.count }</td>--%>
<%--                                        <td><input type="checkbox"></td>--%>
<%--                                        <td>${roler.id }</td>--%>
<%--                                        <td>${roler.name }</td>--%>
<%--                                        <td>--%>
<%--                                            <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>--%>
<%--                                            <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>--%>
<%--                                            <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>--%>
<%--                                        </td>--%>
<%--                                    </tr>--%>
<%--                                </c:forEach>--%>
<%--                            <tr>--%>
<%--                                <td>1</td>--%>
<%--                                <td><input type="checkbox"></td>--%>
<%--                                <td>PM - 项目经理</td>--%>
<%--                                <td>--%>
<%--                                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>--%>
<%--                                    <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>--%>
<%--                                    <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>--%>
<%--                                </td>--%>
<%--                            </tr>--%>
<%--                            <tr>--%>
<%--                                <td>2</td>--%>
<%--                                <td><input type="checkbox"></td>--%>
<%--                                <td>SE - 软件工程师</td>--%>
<%--                                <td>--%>
<%--                                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>--%>
<%--                                    <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>--%>
<%--                                    <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>--%>
<%--                                </td>--%>
<%--                            </tr>--%>
<%--                            <tr>--%>
<%--                                <td>3</td>--%>
<%--                                <td><input type="checkbox"></td>--%>
<%--                                <td>PG - 程序员</td>--%>
<%--                                <td>--%>
<%--                                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>--%>
<%--                                    <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>--%>
<%--                                    <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>--%>
<%--                                </td>--%>
<%--                            </tr>--%>
<%--                            <tr>--%>
<%--                                <td>4</td>--%>
<%--                                <td><input type="checkbox"></td>--%>
<%--                                <td>TL - 组长</td>--%>
<%--                                <td>--%>
<%--                                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>--%>
<%--                                    <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>--%>
<%--                                    <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>--%>
<%--                                </td>--%>
<%--                            </tr>--%>
<%--                            <tr>--%>
<%--                                <td>5</td>--%>
<%--                                <td><input type="checkbox"></td>--%>
<%--                                <td>GL - 组长</td>--%>
<%--                                <td>--%>
<%--                                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>--%>
<%--                                    <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>--%>
<%--                                    <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>--%>
<%--                                </td>--%>
<%--                            </tr>--%>
<%--                            <tr>--%>
<%--                                <td>6</td>--%>
<%--                                <td><input type="checkbox"></td>--%>
<%--                                <td>QA - 品质保证</td>--%>
<%--                                <td>--%>
<%--                                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>--%>
<%--                                    <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>--%>
<%--                                    <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>--%>
<%--                                </td>--%>
<%--                            </tr>--%>
<%--                            <tr>--%>
<%--                                <td>7</td>--%>
<%--                                <td><input type="checkbox"></td>--%>
<%--                                <td>QC - 品质控制</td>--%>
<%--                                <td>--%>
<%--                                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>--%>
<%--                                    <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>--%>
<%--                                    <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>--%>
<%--                                </td>--%>
<%--                            </tr>--%>
<%--                            <tr>--%>
<%--                                <td>8</td>--%>
<%--                                <td><input type="checkbox"></td>--%>
<%--                                <td>SA - 软件架构师</td>--%>
<%--                                <td>--%>
<%--                                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>--%>
<%--                                    <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>--%>
<%--                                    <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>--%>
<%--                                </td>--%>
<%--                            </tr>--%>
<%--                            <tr>--%>
<%--                                <td>8</td>--%>
<%--                                <td><input type="checkbox"></td>--%>
<%--                                <td>CMO / CMS - 配置管理员</td>--%>
<%--                                <td>--%>
<%--                                    <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>--%>
<%--                                    <button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>--%>
<%--                                    <button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>--%>
<%--                                </td>--%>
<%--                            </tr>--%>
                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul class="pagination">
<%--                                        <li class="disabled"><a href="#">上一页</a></li>--%>
<%--                                        <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>--%>
<%--                                        <li><a href="#">2</a></li>--%>
<%--                                        <li><a href="#">3</a></li>--%>
<%--                                        <li><a href="#">4</a></li>--%>
<%--                                        <li><a href="#">5</a></li>--%>
<%--                                        <li><a href="#">下一页</a></li>--%>
                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>
<script type="text/javascript" src="${APP_PATH}/script/menu.js"></script>

<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
        queryPageRole(1);
        showMenu();
    });
    $("tbody .btn-success").click(function(){
        window.location.href = "assignPermission.html";
    });
         var jsonObj={
             "pageno":1,
             "pagesize":3
         }
    function queryPageRole(pageno){
        jsonObj.pageno=pageno
        var loadingIndex=-1;
       $.ajax({
           type:"POST",
           data:jsonObj,
           url:"${APP_PATH}/role/doIndex.do",
           beforeSend:function () {
               loadingIndex=layer.load(2,{time:2000})
               return true;
           },
           success:function (result) {
               if (result.success) {
                   layer.close(loadingIndex);
                   var page=result.page;
                   var datas=page.datas;
                   var content =''
                   $.each(datas, function (i, n) {
                       content += '<tr>'
                       content += '<td>' + (i + 1) + '</td>'
                       content += '<td><input type="checkbox" id="'+n.id+'"></td>'
                       content += ' <td>' + n.name + '</td>'
                       content += '<td>'
                       content += '<button type="button"  class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>'
                       content += '<button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>'
                       content += '<button onclick="deleteRole('+n.id+',\''+n.name+'\')" type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>'
                       content += '</td>'
                       content += '</tr>'

                   });
                   $("tbody").html(content);
                   var contentBar='';
                   if(page.pageno==1)
                   { contentBar+='<li class="disabled"><a href="#">上一页</a></li>';}
                   else{
                       contentBar+='<li><a href="#" onclick="pageChange('+(page.pageno-1)+')">上一页</a></li>'
                   }
                   for(var i=1;i<=page.totalno;i++)
                   {
                       contentBar+='<li'
                       if(i==page.pageno){
                           contentBar+=' class="active" ';
                       }
                       contentBar+='><a href="#" onclick="pageChange('+i+')">'+i+'</a></li>';
                   }
                   if(page.pageno==page.pagesize)
                   {
                       contentBar+='<li class="disabled"><a href="#">下一页</a></li>';

                   }else{
                       contentBar+='<li><a href="#" onclick="pageChange('+(page.pageno+1)+')">下一页</a></li>'
                   }
                   $(".pagination").html(contentBar);


               } else {
                     layer.msg(result.message,{time:1000,icon:5,shift:6})
               }
           },
           error:function() {

               layer.msg("数据加载失败",{time:1000,icon:5,shift:6})
           }
       })
    }
    function pageChange(pageno) {
             queryPageRole(pageno);
    }


    $("#queryBtn").click(function () {
        var queryContent=$("#queryContent").val()
        // jsonObj.queryContent(queryContent);
        if($.trim(queryContent)=="")
        {
            layer.msg("查询条件不能为空，请重新输入！！",{time:2*1000,icon:5,shift:6},function () {
                $("#queryContent").focus()
            })
            return;
        }
        jsonObj.queryContent=$.trim(queryContent)
        queryPageRole(1);
    })

   function deleteRole(id,name) {
             layer.confirm("是否要删除角色？",{icon:3,title:"提示"},function(cindex) {
           $.ajax({
               type:"POST",
               data:{id:id},
               url:"${APP_PATH}/role/delete.do",
               success : function(result){
                   if(result.success){
                       layer.msg("删除"+name+"角色成功!", {time:1000, icon:6}, function(){
                               queryPageRole(1);
                       });
                   }else{
                       layer.msg("删除"+name+"角色失败!", {time:1000, icon:5, shift:6});
                   }
               }
           });

           layer.close(cindex);
           })


   }
   $("#allcheckbox").click(function () {
       var checkedStatus=this.checked;
      // $("tbody tr td input[type='checkbox']").prop("checked",checkedStatus);
       var tbodyCheckbox=$("tbody tr td input[type='checkbox']")
       $.each(tbodyCheckbox,function (i,n) {
           n.checked=checkedStatus;
       })
   })
   //  $("#allcheckbox").click(function () {
   //      var checkedStatus=this.checked;
   //
   //      $("tbody tr td input[type='checkbox']").prop("checked",checkedStatus);//如果使用attr的话，会出bug,选择后不再选择，再选择，会无法选择
   //  })

    $("#deleteBatchBtn").click(function () {

           var checkedId=$("tbody tr td input:checked")
           if(checkedId.length==0)
           {
               layer.msg("至少选择一个用户进行删除操作！请选择需要删除的用户",{time:1000,icon:5,shift:6})
               return;
           }
           var datas={};
           $.each(checkedId,function (i,n) {

               datas["userList["+i+"].id"]=n.id;
               datas["userList["+i+"].name"]="1111";

           })
           layer.confirm("确认要删除这些用户吗？？",{icon:3,title:"提示"},function (cindex) {
               layer.close(cindex)
               $.ajax({
                   type : "POST",
                   //data : idStr,
                   data : datas,
                   url : "${APP_PATH}/role/doDeleteBatch.do",
                   beforeSend : function() {
                       return true ;
                   },
                   success : function(result){
                       if(result.success){
                           window.location.href="${APP_PATH}/role/index.htm";
                       }else{
                           layer.msg("删除用户失败", {time:1000, icon:5, shift:6});
                       }
                   },
                   error : function(){
                       layer.msg("删除失败", {time:1000, icon:5, shift:6});
                   }
               });
           }, function(cindex){
               layer.close(cindex);
           })

       })
</script>
</body>
</html>

