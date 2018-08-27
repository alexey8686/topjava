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
    var enabled = $(form).is(':checked');

    $.ajax({
        url: ajaxUrl + id,
        type: "POST",
        data: "enabled=" + enabled,
        success: function () {
            $(form).closest("tr").attr("data-enabled",enabled);
            successNoty(enabled?"enabled":"disabled");
        }
    });

}