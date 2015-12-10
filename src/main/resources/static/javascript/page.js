$(function ($) {
    $.fn.pageable = function (options) {
        var settings = $.extend({
            url: '',
            fn: function () {
            }
        }, options || {});

        function getPages(currentPage, maxSize, totalPages) {
            var pages = [];

            // Default page limits
            var startPage = 1, endPage = totalPages;
            var isMaxSized = maxSize && maxSize < totalPages;

            // recompute if maxSize
            if (isMaxSized) {
                // Current page is displayed in the middle of the visible ones
                startPage = Math.max(currentPage - Math.floor(maxSize / 2), 1);
                endPage = startPage + maxSize - 1;

                // Adjust if limit is exceeded
                if (endPage > totalPages) {
                    endPage = totalPages;
                    startPage = endPage - maxSize + 1;
                }
            }

            for (var number = startPage; number <= endPage; number++) {
                var page = makePage(number, number, number == currentPage);
                pages.push(page);
            }
            return pages;
        }

        function makePage(number, text, isActive) {
            return {
                number: number,
                text: text,
                active: isActive
            };
        }

        var self = this;

        this.on('click', 'li a', function (e) {
            var source = $(e.target);
            var current = source.html();
            $.ajax({
                url: settings.url
            }).done(function (data) {
                settings.fn(data);
                var pages = getPages(current, 7, 100);
                var ul = $('ul.pageable');
                ul.children().remove();
                pages.forEach(function (e) {
                    var li = $('<li><a href="#"></a></li>');
                    var a = li.find('a').html(e.text);
                    if (e.active)
                        a.addClass('active');
                    ul.append(li);
                });
            });
        });

    };
}(jQuery));