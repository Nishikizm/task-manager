$(function() {
    // console.log("main.js loaded");
    $('.openModal').on('click', function(e) {
        $('#modalOverlay').fadeIn();
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
});