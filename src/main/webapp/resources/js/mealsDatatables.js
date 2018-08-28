var ajaxUrl = "ajax/meal/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
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
                "desc"
            ]
        ]
    });
    makeEditable();
});

function filtration() {
    var form = $("#filterForm")

    $.ajax({
        url: ajaxUrl + "filter",
        type: "GET",
        data: form.serialize(),
        success: function (data) {
            cleanAndAdd(data);
        }
    })

}
