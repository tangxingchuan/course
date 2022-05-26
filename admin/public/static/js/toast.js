const Toast = Swal.mixin({
    toast: true,
    position: 'top-right',
    iconColor: 'white',
    customClass: {
        popup: 'colored-toast'
    },
    showConfirmButton: false,
    timer: 1500,
    timerProgressBar: true,
    title:message,
});





toast={
    success:function (message) {

        Toast.fire({
            icon: 'success',
            title: message
        });
    },

    error:function (message) {

        Toast.fire({
            icon: 'error',
            title: message
        });
    },

    warning:function (message) {

        Toast.fire({
            icon: 'warning',
            title: message
        });
    },
    info:function (message) {

        Toast.fire({
            icon: 'info',
            title: message
        });
    },
    info:function (message) {

        Toast.fire({
            icon: 'info',
            title: message
        });
    },
    question:function (message) {

        Toast.fire({
            icon: 'question',
            title: message
        });
    },


};
