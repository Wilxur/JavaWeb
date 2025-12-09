// 读取 cookie
function getCookie(name) {
    const arr = document.cookie.split("; ");
    for (let c of arr) {
        const kv = c.split("=");
        if (kv[0] === name) return kv[1];
    }
    return null;
}

let startTime = Date.now();

window.addEventListener("beforeunload", () => {
    const uid = getCookie("news_uid");
    const newsId = document.getElementById("newsId").value;
    const categoryId = document.getElementById("categoryId").value;

    const duration = Math.floor((Date.now() - startTime) / 1000);

    navigator.sendBeacon("/behavior/report",
        new URLSearchParams({
            uid: uid,
            newsId: newsId,
            categoryId: categoryId,
            action: "leave",
            duration: duration
        })
    );
});