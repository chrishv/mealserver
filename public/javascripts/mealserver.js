function toggleActiveNavbarItemOn(itemId) {


    var homeMenuItem = document.getElementById('homeMenuItem');
    var personMenuItem = document.getElementById('homeMenuItem');
    var orderMenuItem = document.getElementById('orderMenuItem');
    var vendorMenuItem = document.getElementById('vendorMenuItem');
    
    homeMenuItem.className = "";
    personMenuItem.className = "";
    orderMenuItem.className = "";
    vendorMenuItem.className = "";

    var activeItem = document.getElementById(itemId);

    activeItem.className = "active";

}
