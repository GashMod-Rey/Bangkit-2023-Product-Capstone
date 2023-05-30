function uuidv4() {
    return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) =>
      (
        c ^
        (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))
      ).toString(16)
    );
  }

let id = 0;

document.getElementById("submitBtn").addEventListener("click", () => {
    let postId = uuidv4();
    let inputElem = document.getElementById("pdfFile");
    let file = inputElem.files[0];

    // let blob = file.slice(0, file.size, "application/pdf");
    newFile = new File([file], `${id}_post.pdf`, { type: "application/pdf"});
    id = id+1;

    let formData = new FormData();
    formData.append("pdfFile", newFile);

    fetch("/upload", {
        method: "POST",
        body: formData,
    })
    .then((res) => res.text());
    // .then(takeLink());

    setTimeout(function() {
      location.reload();
    }, 2000);
    // takeLink();
});

document.getElementById("showBtn").addEventListener("click", () =>{
  fetch("/upload")
    .then((res) => res.json())
    .then((x) => {
      let y = x[0].length - 1;
      console.log(x[0][y]);
      console.log("https://storage.googleapis.com/bucket_pdf33/" + x[0][y].id);
    });
})

// function takeLink(){
//   fetch("/upload")
//     .then((res) => res.json())
//     .then((x) => {
//       for (y = 0; y < x[0].length; y++) {
//         console.log(x[0][y]);
//         console.log("https://storage.googleapis.com/bucket_pdf33/" + x[0][y].id);
//       }
//     });
// }