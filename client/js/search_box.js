$(function () {
  var formatPackage = function(package) {
    if (package.loading) return package.text;

    var markup = "<div class='clearfix'>";

    $.each(package, function(key, value) {
      // if(key.match(/^_/)) { continue; }
      // markup += "<div search-box-key>" + key + ":</div>";
      // markup += "<div search-box-value>" + value + ":</div>";
    });

    return markup;
  }

  var formatPackageSelection = function(package) {
    return package.name
  }

  $('.search-box').select2({
    width: 'resolve',
    ajax: {
      url: "http://resprout.cfapps.io/api/packages/search/findByNameContainingIgnoreCase",
      dataType: 'json',
      delay: 250,
      data: function (params) {
        return {
          name: params.term, // search term
          page: params.page
        };
      },
      processResults: function (data, params) {
        // parse the results into the format expected by Select2
        // since we are using custom formatting functions we do not need to
        // alter the remote JSON data, except to indicate that infinite
        // scrolling can be used
        params.page = params.page || 1;

        debugger;

        return {
          results: data._embedded.homebrew_packages,
          pagination: {
            more: (params.page * 30) < data.total_count
          }
        };
      },
      cache: true
    },
    escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
    minimumInputLength: 1,
    templateResult: formatPackage, // omitted for brevity, see the source of this page
    templateSelection: formatPackageSelection // omitted for brevity, see the source of this page
  });
});
