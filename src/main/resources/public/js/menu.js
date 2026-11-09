const priceText=document.querySelector("#priceText");
const bottomSelect=document.querySelector("select[name='bottom']");
const topSelect=document.querySelector("select[name='top']");


function updatePrice() {
    const bottomPrice = Number(bottomSelect.selectedOptions[0].dataset.price);
    const topPrice = Number(topSelect.selectedOptions[0].dataset.price);
    priceText.textContent = bottomPrice + topPrice;
    console.log(bottomPrice + topPrice);
}

bottomSelect.addEventListener("change", updatePrice);
topSelect.addEventListener("change", updatePrice);
updatePrice();