/**
 * Concatena o código do título na action do form ao abrir o modal.
 * 
 */
$('#confirmacaoExclusaoModal').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	// pega o código
	var codigoTitulo = button.data('codigo');
	// pega a descrição do título
	var descricaoTitulo = button.data('descricao')
	// pega o form
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	
	if (!action.endsWith('/')) {
		action += '/';
	}
	
	// aplica o código na action do form
	form.attr('action', action + codigoTitulo);
	
	// mostra mensagem
	modal.find('.modal-body span').html('Tem certeza que deseja exlucir o título <strong>' + descricaoTitulo + '</strong>?');
});