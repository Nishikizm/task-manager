$(function() {
    // console.log("main.js loaded");

    const FORM_FIELDS = ['title', 'description', 'year', 'month', 'day', 'time', 'completed'];
    const $modal = $('#modalOverlay');
    let snapshot = {};

    $(document).on('change', '.check', function() {
        $(this).closest('tr').toggleClass('is-active');
    });

    $('.openModal').on('click', function(e) {
        $.get('tasks/create', function(html) {
            $('#formFragment').replaceWith(html);
            $modal.fadeIn();
            $('#title').focus();
        });
    });

    $('.closeModal').on('click', function(e) {
        modalClose();
    });

    $(document).on('keydown', function(e) {
        if(e.key === 'Escape') { modalClose(); }
    });

    $modal.on('click', function(e) {
        const $back = $(e.target).closest('#modalForm');
        if(!$back.length) { modalClose(); }
    });

    $(document).on('click', '.editBtn', function(e) {
        $.get('tasks/patch/' + $(this).val(), function(html) {
            $('#formFragment').replaceWith(html);
            $modal.fadeIn();
            takeSnapshot($('#formFragment'));
        });
    });

     $(document).on('click', '#reset', function(e) {
        $.get('tasks/patch/' + $(this).val(), function(html) {
            $('#formFragment').replaceWith(html);
        });
    });

    // $(document).on('change', 'td input[type=checkbox]', function(e) {
        // $(this).prop('disabled', true);
    // });

    $(document).on('submit', '#form', function(e) {

        e.preventDefault();

        const $form = $('#form');
        const $btn = $form.find('#formBtn');

        $btn.prop('disabled', true).text('Saving...');

        if($form.data('method') === 'POST') {
            $.ajax({
                url: $form.attr('action'), 
                type: $form.attr('data-method'), 
                data: $form.serialize()
            })
            .done(function() {
                $.get('/tasks/list', function(li) {
                    $('#list').replaceWith(li);
                    modalClose();
                });
            })
            .fail(function() {
                alert('エラー');
            })
            .always(function() {
                $btn.prop('disabled', false).text('Create');
            })
        } else if($form.data('method') === 'PATCH') {

        }
    });

    $('#delete').on('click', function(e) {

        e.preventDefault();

        const $btn = $(this);
        const idList = $('.check:checked').map(function() {
            return $(this).val();
        }).get();
        if(!idList.length) {
            alert('削除対象を選択してください');
            return;
        }

        $btn.prop('disabled', true).text('Saving...');
        $.ajax({
            url: '/tasks', 
            type: 'DELETE', 
            data: JSON.stringify(idList), 
            contentType: 'application/json; charset=UTF-8', 
            dataType: 'json'
        })
        .done(function(data) {
            $.get('/tasks/list', function(li) {
                $('#list').replaceWith(li);
            })
            setTimeout(function() {
                alert(data + '件を削除しました');
            }, 100);
        })
        .fail(function() {
            alert('エラー');
        })
        .always(function() {
            $btn.prop('disabled', false).text('Delete');
        })
    });

    function takeSnapshot($form) {
        for(const f of FORM_FIELDS) {
            snapshot[f] = normalize($form.find(`[name='${f}']`));
        }
    }

    function normalize($target) {
        const field = $target.attr('name');
        const value = $target.val();

        if($target.is(':checkbox')) { return $target.prop('checked'); }
        if(value === null || value.trim() === '') { return null; }
        if(field === 'year' || field === 'month' || field === 'day') { return Number(value); }
        return String(value);
    }

    function modalClose() {
        snapshot = {};
        $modal.fadeOut();
    }

});