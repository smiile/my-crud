var Global = {};
$(document).ready(function () {
    // Initialize row_index - used for selection control
    Global.row_index = -1;
    Global.changePasswordRowIndex = -1;

    $('#changePasswordModal').on('hidden.bs.modal', function (e) {
        // Reset previous error states and clear input
        resetErrorStates(['#oldPasswordInput', '#newPasswordInput', '#repeatPasswordInput']);
        clearInputs(['#oldPasswordInput', '#newPasswordInput', '#repeatPasswordInput']);
        $('#chagePasswordErrorMsg').html("");
    });

    $('#addUserModal').on('hidden.bs.modal', function (e) {
        // Reset previous error states and clear input
        resetErrorStates(['#emailRegisterInput', '#nameRegisterInput', '#passwordRegisterInput', '#repeatPasswordRegisterInput']);
        clearInputs(['#emailRegisterInput', '#nameRegisterInput', '#passwordRegisterInput', '#repeatPasswordRegisterInput']);
        $('#addUserErrorMsg').html("");
    });

    $('#addUserBtn').click(function () {
        $('#addUserModal').modal('show');
    });

    $('body').on('click', 'a.changePassword', function (event) {
        event.preventDefault();
        // 
        Global.changePasswordRowIndex = $(this).parent().parent().index();
        console.log(Global.changePasswordRowIndex);
        $('#changePasswordModal').modal('show');
    });

    $('body').on('click', 'a.update', function (event) {
        event.preventDefault();
        resetErrorStates(['#editName', '#editEmail']);

        var name = $(this).parent().siblings('td.name').html();
        var email = $(this).parent().siblings('td.email').html();

        // Selection control
        if (Global.row_index != -1) {
            // Deselect and remove style
            var alreadySelected = $(this).parent().parent().siblings('.highlight');

            if (alreadySelected) {
                alreadySelected.removeClass('highlight');
            }

            Global.row_index = -1;
        }

        Global.row_index = $(this).parent().parent().index();

        $('#editName').val(name);
        $('#editEmail').val(email);

        // Style selected row
        $(this).parent().parent().addClass('highlight');
    });


    $('body').on('click', 'a.delete', function (event) {
        event.preventDefault();
        clearEditForm();

        // Selection control
        if (Global.row_index != -1) {
            // Deselect and remove style
            var alreadySelected = $(this).parent().parent().siblings('.highlight');

            if (alreadySelected) {
                alreadySelected.removeClass('highlight');
            }

            Global.row_index = -1;
        }

        // Set current row
        Global.row_index = $(this).parent().parent().index();

        // Style selected row
        var row = $('#user tbody tr').eq(Global.row_index);
        row.addClass('highlight');

        var id = row.children().find("input[type=hidden]").val();

        var certaintyCheck = confirm("Are you sure you want to delete this user?");

        if (!certaintyCheck) {
            $(this).parent().parent().removeClass('highlight');
            Global.row_index = -1;
            clearEditForm();
            return;
        }

        $.ajax({
            url: "${pageContext.request.contextPath}/api/deleteUser",
            cache: false,
            method: "POST",
            data: {id: id},
            success: function (response) {
                console.log(response);
                var parsedResponse = jQuery.parseJSON(response);

                if (parsedResponse.status === "OK") {
                    row.remove();
                    clearEditForm();

                    // Check if table is empty
                    var rowCount = $('#user >tbody >tr').length;

                    if (rowCount === 0) {
                        $('#user').css("display", "none");
                        $('#editForm').css("display", "none");
                        $('#noUsers').html("<b>none</b>");
                    }
                } else {
                    $('#errorMsg').html(parsedResponse.data)
                }
            }
        });
    });


    $('#submitEdit').click(function () {
        // Validation
        if (validateEditForm()) {
            console.error("Error in validation");
            console.log("row_index: " + Global.row_index);
            console.log("editedEmail: " + editedEmail);
            console.log("editedName: " + editedName);
            return;
        }

        // Prepare request's values
        var editedEmail = $('#editEmail').val();
        var editedName = $('#editName').val();

        var row = $('#user tbody tr').eq(Global.row_index);
        var id = row.children().find("input[type=hidden]").val();

        $.ajax({
            url: "${pageContext.request.contextPath}/api/updateUser",
            cache: false,
            method: "POST",
            data: {id: id, name: editedName, email: editedEmail},
            success: function (response) {
                console.log(response);
                var parsedResponse = jQuery.parseJSON(response);

                if (parsedResponse.status === "OK") {
                    // Change values in table
                    row.children('td.name').html(editedName);
                    row.children('td.email').html(editedEmail);

                    // Clear input fields
                    clearEditForm();

                    // Unselect row
                    row.removeClass('highlight');

                    // Clear errors
                    clearEditForm();

                    // Reset row_index
                    Global.row_index = -1;
                } else {
                    $('#errorMsg').html(parsedResponse.data);
                }
            }
        });
    });

    $('#submitChangePassword').click(function () {
        var changePasswordRow = $('#user tbody tr').eq(Global.changePasswordRowIndex);

        var id = changePasswordRow.children().find("input[type=hidden]").val();
        var oldPassword = $('#oldPasswordInput').val();
        var newPassword = $('#newPasswordInput').val();
        var repeatPassword = $('#repeatPasswordInput').val();

        // Reset previous error states
        resetErrorStates(['#oldPasswordInput', '#newPasswordInput', '#repeatPasswordInput']);

        var hasError = false;

        if (!oldPassword) {
            $('#oldPasswordInput').parent().addClass('has-error');
            hasError = true;
        }

//                if (!newPassword) {
//                    $('#newPasswordInput').parent().addClass('has-error');
//                    hasError = true;
//                }

        if (newPassword !== repeatPassword) {
            $('#repeatPasswordInput').parent().addClass('has-error');
            hasError = true;
        }

        if (hasError) {
            return;
        }

        $.ajax({
            url: "${pageContext.request.contextPath}/api/changePassword",
            cache: false,
            method: "POST",
            data: {id: id, password: newPassword},
            success: function (response) {
                console.log(response);
                var parsedResponse = jQuery.parseJSON(response);

                if (parsedResponse.status == "OK") {
                    $('#changePasswordModal').modal('hide');
                    alert("Password changed successfully!");
                } else {
                    $('#chagePasswordErrorMsg').html(parsedResponse.data);
                }
            }
        });
    });

    $('#submitAddUser').click(function () {
        var email = $("#emailRegisterInput").val();
        var name = $("#nameRegisterInput").val();
        var password = $('#passwordRegisterInput').val();
        var repeatPassword = $("#repeatPasswordRegisterInput").val();

        resetErrorStates(['#emailRegisterInput', '#nameRegisterInput', '#passwordRegisterInput', '#repeatPasswordRegisterInput']);

        //TODO: Extract "not-empty" validation function for form fields
        var hasError = false;

        if (!email) {
            $('#emailRegisterInput').parent().addClass('has-error');
            hasError = true;
        }

        if (!name) {
            $('#nameRegisterInput').parent().addClass('has-error');
            hasError = true;
        }

        if (!password) {
            $('#passwordRegisterInput').parent().addClass('has-error');
            hasError = true;
        }

        if (!repeatPassword || (repeatPassword !== password)) {
            $('#repeatPasswordRegisterInput').parent().addClass('has-error');
            hasError = true;
        }

        if (hasError) {
            return;
        }

        $.ajax({
            url: "${pageContext.request.contextPath}/api/addUser",
            cache: false,
            method: "POST",
            data: {email: email, name: name, password: password},
            success: function (response) {
                console.log(response);
                var parsedResponse = jQuery.parseJSON(response);

                if (parsedResponse.status === "OK") {
                    // Check if table is empty(hidden) and show it
                    if ($('#user >tbody >tr').length === 0) {
                        $('#user').css("display", "table");
                        $('#editForm').css("display", "block");
                        $('#noUsers').html("");
                    }
                    $('#user tr:last').after(parsedResponse.data);
                    $('#addUserModal').modal('hide');
                    alert("User added successfully!");
                } else {
                    $('#addUserErrorMsg').html(parsedResponse.data);
                }
            }
        });

    });

    function resetErrorStates(arr) {
        $.each(arr, function (index, value) {
            $(value).parent().removeClass('has-error');
        });
    }

    function clearInputs(arr) {
        $.each(arr, function (index, value) {
            $(value).val('');
        });
    }

    function validateEditForm() {
        var hasError = false;

        if (Global.row_index == -1) {
            hasError = true;
        }

        if ($('#editEmail').val() === "") {
            hasError = true;
            $('#editEmail').parent().addClass('has-error');
        }

        if ($('#editName').val() === "") {
            hasError = true;
            $('#editName').parent().addClass('has-error');
        }

        return hasError;
    }

    function clearEditForm() {
        $('#editEmail').val("");
        $('#editName').val("");
        $('#errorMsg').html("");
    }
});