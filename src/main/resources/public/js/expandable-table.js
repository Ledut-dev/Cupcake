function enableExpandableRows(tableId) {
    const table = document.getElementById(tableId);
    if (!table) return;

    table.addEventListener('click', function(e) {
        // Find the closest ancestor 'tr' that is expandable
        const expandableRow = e.target.closest('tr.row-expandable');
        if (!expandableRow) return;

        // Find the very next TR element, which is the details row
        const detailsRow = expandableRow.nextElementSibling;
        if (detailsRow && detailsRow.classList.contains('order-details')) {
            expandableRow.classList.toggle('open');
            detailsRow.classList.toggle('visible');
        }
    });
}

enableExpandableRows('placed-orders-table');
enableExpandableRows('approved-orders-table');