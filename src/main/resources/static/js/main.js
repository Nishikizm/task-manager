$(function() {
    // console.log("main.js loaded");

    $(document).on('change', '.check', function() {
        $(this).closest('tr').toggleClass('is-active');
    });

    $('.openModal').on('click', function(e) {
        $.get('tasks/form', function(html) {
            $('#formFragment').replaceWith(html);
            $('#modalOverlay').fadeIn();
            $('#title').focus();
        });
    });

    $('.closeModal').on('click', function(e) {
        $('#modalOverlay').fadeOut();
    });

    $(document).on('keydown', function(e) {
        if(e.key === 'Escape') { $('#modalOverlay').fadeOut(); }
    });

    $('#modalOverlay').on('click', function(e) {
        const $back = $(e.target).closest('#modalForm');
        if(!$back.length) { $('#modalOverlay').fadeOut(); }
    });

    // $(document).on('change', 'td input[type=checkbox]', function(e) {
        // $(this).prop('disabled', true);
    // });

    $(document).on('submit', '#form', function(e) {

        e.preventDefault();

        const $form = $('#form');
        const $btn = $form.find('#formBtn');

        $btn.prop('disabled', true).text('Saving...');
        $.ajax({
            url: $form.attr('action'), 
            type: $form.attr('method'), 
            data: $form.serialize()
        })
        .done(function() {
            $.get('/tasks/list', function(li) {
                $('#list').replaceWith(li);
                $('#modalOverlay').fadeOut();
            });
        })
        .fail(function() {
            alert('エラー');
        })
        .always(function() {
            $btn.prop('disabled', false).text('Submit');
        })
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

});