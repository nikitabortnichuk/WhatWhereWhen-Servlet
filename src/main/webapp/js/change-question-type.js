
document.getElementById("question_type")
    .onchange = function () {
    let b = {
            "WITH_VARIANTS": "variants",
            "NO_VARIANTS": "answer"
        }, c = this.value,
        a;
    for (a in b) document.getElementById(b[a])
        .style.display = c === a ? "block" : "none"
};