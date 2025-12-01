/** ------------------------------------------
 main.js
 
 javascript functions for practical work
 
 JY Martin
 Ecole Centrale Nantes
 ------------------------------------------ */

// -----------------------------------------------------------------------------

/**
 * Apply success when remove borrow
 * @param {type} theResult
 * @param {type} buttonRef
 * @returns {undefined}
 */
function returnBorrowSuccess(theResult, buttonRef) {
  if (buttonRef !== null) {
    // Get TD that owns the button
    var refTD = buttonRef.parentElement;
    if (refTD !== null) {
      // Remove button
      refTD.removeChild(buttonRef);
      // Set return date
      var currentDate = new Date(((Date)(theResult.returnedValue)));
      var currentDateStr = currentDate.toLocaleDateString();
      var text = document.createTextNode(currentDateStr);
      refTD.appendChild(text);
    }
  }
}

/**
 * Launch ajax call to delete a book
 * @param {type} buttonRef
 * @param {type} borrowId
 * @returns {undefined}
 */
function returnBorrow(buttonRef, borrowId) {
  if (borrowId > 0) {
    // Collect data - empty

    // Ajax call
    $.ajax({
      url: "returnborrow.do",
      method: "POST",
      data: {
        "id": borrowId,
      },
      success: function (theResult) {
        returnBorrowSuccess(theResult, buttonRef);
      },
      error: function (theResult, theStatus, theError) {
        console.log("Error : " + theStatus + " - " + theResult);
      }
    });
  }
}

