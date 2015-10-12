<%@ page language="java" contentType="text/html; charset=US-ASCII"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
        <title>Users</title>
    </head>
    <body>
        <button type="button" id="addUserBtn" class="btn btn-primary">Add user</button>
        <c:choose>
            <c:when test="${not empty users}">
                <h4>Existing users: <span id="noUsers"></span></h4>
                <span id="errorMsg"></span>
                <div style="width: 700px;">
                    <display:table name="users" id="user" class="table">
                        <display:column property="name" class="name" />
                        <display:column property="email" class="email" />
                        <display:column title="Password">
                            <a href="#" class="changePassword">Change password</a>
                        </display:column>
                        <display:column property="registerDate" title="Registered at" />
                        <display:column title="Action">
                            <a href="#" class="update">Update</a> | 
                            <a href="#" class="delete">Delete</a>
                        </display:column>
                        <display:column>
                            <input type="hidden" name="id" value="${user.id}" />
                        </display:column>
                    </display:table>
                </div>
            </c:when>
            <c:otherwise>
                <c:redirect url="${pageContext.request.contextPath}" />
            </c:otherwise>
        </c:choose>

        <div style="margin-top: 20px; margin-bottom: 30px;">
            <form class="form-inline" action="" id="editForm">
                <div class="form-group">
                    <input type="text" id="editName" name="name" class="form-control" placeholder="Name" />
                </div>
                <div class="form-group">
                    <input type="text" id="editEmail" name="email" class="form-control" placeholder="E-mail" />
                </div>

                <input type="button" id="submitEdit" class="btn btn-default" value="Edit" />
            </form>
        </div>

        <!-- Modal --> 
        <div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog"
             aria-labelledby="changePasswordModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="changePasswordModalLabel">Change password</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <span id="chagePasswordErrorMsg"></span>
                            <div class="form-group">
                                <label for="oldPasswordInput">Old Password</label> <input
                                    type="password" class="form-control" id="oldPasswordInput"
                                    name="oldPassword" placeholder="Password">
                            </div>
                            <div class="form-group">
                                <label for="newPasswordInput">New password</label> <input
                                    type="password" class="form-control" id="newPasswordInput"
                                    name="newPassword" placeholder="Password">
                            </div>
                            <div class="form-group">
                                <label for="repeatPasswordInput">Repeat password</label> <input
                                    type="password" class="form-control" id="repeatPasswordInput"
                                    name="repeatPassword" placeholder="Again...">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" id="submitChangePassword" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="addUserModal" tabindex="-1" role="dialog"
             aria-labelledby="addUserModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="addUserModalLabel">Add user</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <span id="addUserErrorMsg"></span>
                            <div class="form-group">
                                <label for="emailRegisterInput">E-mail</label> <input
                                    type="text" class="form-control" id="emailRegisterInput"
                                    name="email" placeholder="E-mail">
                            </div>
                            <div class="form-group">
                                <label for="nameRegisterInput">Name</label> <input
                                    type="text" class="form-control" id="nameRegisterInput"
                                    name="name" placeholder="Name">
                            </div>
                            <div class="form-group">
                                <label for="passwordRegisterInput">Password</label> <input
                                    type="password" class="form-control" id="passwordRegisterInput"
                                    name="password" placeholder="Password">
                            </div>
                            <div class="form-group">
                                <label for="repeatPasswordRegisterInput">Repeat password</label> <input
                                    type="password" class="form-control" id="repeatPasswordRegisterInput"
                                    name="repeatPassword" placeholder="Again...">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" id="submitAddUser" class="btn btn-primary">Submit</button>
                    </div>
                </div>
            </div>
        </div>


    <style type="text/css">
        .highlight { background-color: #E0E0E0 }
        #errorMsg, #chagePasswordErrorMsg, #addUserErrorMsg { color: red };
    </style>


    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/data/user/script.js"></script>
</body>
</html>