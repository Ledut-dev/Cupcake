// Enables expandable/collapsible rows for a table.
function enableExpandableRows(tableId) {
    const table = document.getElementById(tableId);
    if (!table) return;

    table.addEventListener('click', function(e) {
        // Find the closest ancestor 'tr' that is expandable
        const expandableRow = e.target.closest('tr.row-expandable');
        if (!expandableRow) return;

        // Find the very next TR element, which is the details row
        const detailsRow = expandableRow.nextElementSibling;
        if (detailsRow && detailsRow.classList.contains('row-details')) {
            expandableRow.classList.toggle('open');
            detailsRow.classList.toggle('visible');
        }
    });
}

// Initialize the functionality
enableExpandableRows('orderHistoryTable');
