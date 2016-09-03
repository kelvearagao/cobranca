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
	modal.find('.modal-body span').html(
			'Tem certeza que deseja exlucir o título <strong>'
					+ descricaoTitulo + '</strong>?');
});

$(function() {
	// aplica tooltip
	$('[rel="tooltip"]').tooltip();

	// aplica mascara da moeda
	$('.js-currency').maskMoney({
		decimal : ',',
		thousands : '.',
		allowZero : true
	});

	$('.js-atualizar-status').on('click', function(event) {
		event.preventDefault();

		var botaoReceber = $(event.currentTarget);
		var urlReceber = botaoReceber.attr('href');

		var response = $.ajax({
			url : urlReceber,
			type : 'PUT'
		});

		response.done(function(response) {
			var codigoTitulo = botaoReceber.data('codigo');

			$('[data-role=' + codigoTitulo + ']').html(
					'<span class="label label-success">' + response
							+ '</span>');

			botaoReceber.hide();
		});

		response.fail(function(e) {
			console.log(e);
			alert("Erro ao receber a cobrança");
		})
	});
});