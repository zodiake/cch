/**
 * Created by yagamai on 15-12-23.
 */
$(function ($) {
    $.fn.pageable = function (options) {
        var settings = $.extend({
            url: '',
            parameter: function () {
            },
            fn: function () {
            }
        }, options || {});

        function getPages(currentPage, maxSize, totalPages) {
            var pages = [];

            // Default page limits
            var startPage = 1, endPage = totalPages == 0 ? 1 : totalPages;
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

        var current;

        this.on('click', 'li a', function (e) {
            var source = $(e.target);
            var page = Number(source.html())-1;
            var parameter = settings.parameter();
            if (source.attr('aria-label') == 'Previous' && current != null) {
                page = current - 1;
                if (page == 0)
                    page = 1;
            } else if (source.attr('aria-label') == 'Next' && current != null) {
                page = current + 1;
            }

            parameter.page = page;
            current = page;
            parameter.size = 10;
            $.ajax({
                url: settings.url,
                data: parameter
            }).done(function (data) {
                settings.fn(data);
                var pages = getPages(current, 7, data.obj.totalPages);
                var ul = $('ul.pagination');
                ul.children().remove();
                pages.forEach(function (e) {
                    var li = $('<li><a href="#"></a></li>');
                    var a = li.find('a').html(e.text);
                    if (e.active) {
                        li.addClass('active');
                    }
                    ul.append(li);
                });
                ul.append('<li><a href="#" aria-label="Next"><span aria-hidden="true">»</span></a></li>');
                ul.prepend('<li><a href="#" aria-label="Previous"><span aria-hidden="true">«</span></a></li>')
            });
        });

    };
}(jQuery));
