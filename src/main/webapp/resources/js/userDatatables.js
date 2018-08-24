var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});

function setEnable(id) {
    var form = "#" + id;
    if ($(form).is(':checked')) {
        alert("checked");
    }
    else {
        alert("unchecked");
    }
    /*var form = $("${user.id}");
    $.ajax({
      url:ajaxUrl+ "enable",
      type:"POST",
      data: form.serialize(),
      success:function () {
          updateTable()
      }
    })*/
}