$(function() {
    // console.log("main.js loaded");
    $('.openModal').on('click', function(e) {
        console.log("open clicked");
        $('.modalOverlay').fadeIn();
    });
    $('.closeModal').on('click', function(e) {
        $('.modalOverlay').fadeOut();
    });
});