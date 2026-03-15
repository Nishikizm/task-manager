$(function() {
    // console.log("main.js loaded");
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

    $(document).on('change', 'td input[type=checkbox]', function(e) {
        $(this).prop('disabled', true);
    });

    $(document).on('submit', '#form', function(e) {
        const $form = $('#form');
        const $btn = $form.find('#formBtn');

        e.preventDefault();
        $btn.prop('disabled', true).text('Saving...');
        $.ajax({
            url: $form.attr('action'), 
            type: $form.attr('method'), 
            data: $form.serialize()
        })
        .done(function() {
            $.get('tasks/list', function(li) {
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
});