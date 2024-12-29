window.onload = () => {
    let message = decodeURIComponent(window.location.search.replace('?message=', ''));
    if (message !== '') {
        document.getElementById('error-message').innerHTML = message;
    }
}