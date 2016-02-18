$(function () {
    var formatPackage = function (package) {
        if (package.loading) return package.text;

        var markup = "<table class='clearfix'>";

        $.each(package, function (key, value) {
            if (!key.match(/^_/)) {

                markup += "<tr class==\"search-box-result\">";
                markup += "<td class=\"search-box-key\">" + key + "</td>";
                markup += "<td class=\"search-box-value\">" + value + "</td>";
                markup += "</tr>";
            }
        });

        markup += "</table>";
        return markup;

    };

    var formatPackageSelection = function (package) {
        return package.name;
    };

            //url: "http://resprout.cfapps.io/api/packages/search/findByNameContainingIgnoreCase",
    $('.search-box').select2({
        width: 'resolve',
        ajax: {
            url: "http://localhost:8080/api/packages/search/findByNameContainingIgnoreCase",
            dataType: 'json',
            delay: 250,
            data: function (params) {
                return {
                    name: params.term, // search term
                    page: params.page
                };
            },
            processResults: function (data, params) {
                // parse the results into the format expected by Select2s
                // since we are using custom formatting functions we do not need to
                // alter the remote JSON data, except to indicate that infinite
                // scrolling can be used
                params.page = params.page || 1;

                newResults = $.map(data._embedded.homebrew_packages, function(value) {
                    value.id = value.package_type + "." + value.name;
                    return value;
                });

                return {
                    results: newResults,
                    pagination: {
                        more: (params.page * 30) < data.total_count
                    }
                };
            },
            cache: true
        },
        escapeMarkup: function (markup) {
            return markup;
        }, // let our custom formatter work
        minimumInputLength: 1,
        templateResult: formatPackage,
        templateSelection: formatPackageSelection
    });
});
