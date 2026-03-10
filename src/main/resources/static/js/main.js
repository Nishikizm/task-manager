$(function() {
    // console.log("main.js loaded");
    $('.openModal').on('click', function(e) {
        $('#modalOverlay').fadeIn();
    });

    $('.closeModal').on('click', function(e) {
        console.log("close clicked");
        $('#modalOverlay').fadeOut();
    });

    $(document).on('keydown', function(e) {
        if(e.key === 'Escape') { $('#modalOverlay').fadeOut(); }
    });

    $('#modalOverlay').on('click', function(e) {
        console.log("modalOverlay clicked");
        const $back = $(e.target).closest('#modalForm');
        if(!$back.length) { $('#modalOverlay').fadeOut(); }
    });
});