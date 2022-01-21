
function toggleNav() {
    var elem = $('nav ul');
    if (elem.css('display') == 'block')
        elem.css('display', 'none');
    else elem.css('display', 'block');
}


function popModal(modal_id) {
	var modal = $('#'+modal_id);
	var close = $('#'+modal_id+'_close');
	modal.css('display', 'block');
	close.click(function() {
	  modal.css('display', 'none');
	});
	window.onclick = function(event) {
		if (event.target.id == modal_id) {
			modal.css('display', 'none');
		}
	};
}

function copy_to_clipboard(text) {
	var textArea = document.createElement("textarea");
    textArea.value = text;
    document.body.appendChild(textArea);
    textArea.select();
	document.execCommand('copy');
	textArea.remove();
}
