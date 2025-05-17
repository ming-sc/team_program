import request from "@/apis/request";

export function search(current, size, keyword) {
    return request({
        url: "/file/search",
        method: "get",
        params: {
            current: current,
            size: size,
            keyword: keyword
        }
    });
}

export function upload(file, fileName, type = 0) {
    return request({
        url: "/file/upload",
        method: "post",
        data: {
            file: file,
            fileName: fileName,
            type: type
        },
        headers: {
            "Content-Type": "multipart/form-data"
        }
    });
}