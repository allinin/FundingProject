
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH }/css/main.css">
    <link rel="stylesheet" href="${APP_PATH }/css/doc.min.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="user.html">众筹平台 - 用户维护</a></div>
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
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" class="form-inline">
                        <div class="form-group">
                            <label >未分配角色列表</label><br>
                            <select id="leftRoleList" class="form-control" multiple size="10" style="width:100px;overflow-y:auto;">
<%--                                <option value="pm">PM</option>--%>
<%--                                <option value="sa">SA</option>--%>
<%--                                <option value="se">SE</option>--%>
<%--                                <option value="tl">TL</option>--%>
<%--                                <option value="gl">GL</option>--%>
                                 <c:forEach items="${leftRole}" var="role">
                                     <option value="${role.id}">${role.name}</option>
                                 </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul id="roleBtn">
                                <li id="leftToRight" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="rightToLeft" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label >已分配角色列表</label><br>
                            <select id="rightRoleList" class="form-control" multiple size="10" style="width:100px;overflow-y:auto;">
<%--                                <option value="qa">QA</option>--%>
<%--                                <option value="qc">QC</option>--%>
<%--                                <option value="pg">PG</option>--%>
                                <c:forEach items="${rightRole}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">帮助</h4>
            </div>
            <div class="modal-body">
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题1</h4>
                    <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                </div>
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题2</h4>
                    <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
                </div>
            </div>
            <!--
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            -->
        </div>
    </div>
</div>
<script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH }/script/docs.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/script/menu.js"></script>
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>

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

    });




    //分配角色
    $("#leftToRight").click(function(){
        var selectedOptions = $("#leftRoleList option:selected");

        var jsonObj = {
            userid : "${param.id}"
        };

        $.each(selectedOptions,function(i,n){
            jsonObj["ids["+i+"]"] = this.value ;
        });

        var index = -1 ;
        $.ajax({
            type : "POST",
            data : jsonObj,
            url : "${APP_PATH}/user/doAssignRole.do",
            beforeSend : function(){
                index = layer.load(2, {time: 10*1000});
                return true ;
            },
            success : function(result){
                layer.close(index);
                if(result.success){
                    $("#rightRoleList").append(selectedOptions.clone());
                    selectedOptions.remove();
                }else{
                    layer.msg(result.message, {time:1000, icon:5, shift:6});
                }
            },
            error : function(){
                layer.msg("操作失败!", {time:1000, icon:5, shift:6});
            }

        });


    });


    $("#rightToLeft").click(function () {
        var selectedOptions=$("#rightRoleList option:selected")
        if(selectedOptions.length==0)
        {layer.confirm("请选择需要分配的角色",{time:2000,icon:6})
        }else
        {
            var jsonObj={
                userid: "${param.id}"
            }
            $.each(selectedOptions,function (i,n) {
                jsonObj["ids["+i+"]"]=this.value
            })

            $.ajax({
                type:"POST",
                data:jsonObj,
                url:"${APP_PATH}/user/doDeleteRole.do",
                beforeSend:function () {
                    loadIndex=layer.load(2,{time:2000})
                    return true;
                },
                success:function (result) {
                    layer.close(loadIndex)
                    if(result.success)
                    {
                        $("#leftRoleList").append(selectedOptions.clone())
                        selectedOptions.remove()
                    }else{
                        layer.msg(result.message,{time:2000,icon:5,shift:6})
                    }

                },
                error:function () {
                    layer.msg("撤销角色失败！！",{time:2000,icon:5,shift:6})
                }
            })


        }


    })

    <%--//取消角色--%>
    <%--$("#rightToLeftBtn").click(function(){--%>

    <%--    var selectedOptions = $("#rightRoleList option:selected");--%>

    <%--    var jsonObj = {--%>
    <%--        userid : "${param.id}"--%>
    <%--    };--%>

    <%--    $.each(selectedOptions,function(i,n){--%>
    <%--        jsonObj["ids["+i+"]"] = this.value ;--%>
    <%--    });--%>

    <%--    var index = -1 ;--%>
    <%--    $.ajax({--%>
    <%--        type : "POST",--%>
    <%--        data : jsonObj,--%>
    <%--        url : "${APP_PATH}/user/doDeleteRole.do",--%>
    <%--        beforeSend : function(){--%>
    <%--            index = layer.load(2, {time: 10*1000});--%>
    <%--            return true ;--%>
    <%--        },--%>
    <%--        success : function(result){--%>
    <%--            layer.close(index);--%>
    <%--            if(result.success){--%>
    <%--                $("#leftRoleList").append(selectedOptions.clone());--%>
    <%--                selectedOptions.remove();--%>
    <%--            }else{--%>
    <%--                layer.msg(result.message, {time:1000, icon:5, shift:6});--%>
    <%--            }--%>
    <%--        },--%>
    <%--        error : function(){--%>
    <%--            layer.msg("操作失败!", {time:1000, icon:5, shift:6});--%>
    <%--        }--%>

    <%--    });--%>

    <%--});--%>
</script>
</body>
</html>
