import request from "@/apis/request";

export function getPractices(count) {
    return request({
        url: "/vocabulary/getPractices",
        method: "get",
        params: {
            count: count
        }
    });
}

export function submit(data) {
    return request({
        url: "/vocabulary/submit",
        method: "post",
        data: data,
        headers: {
            "Content-Type": "application/json"
        }
    });
}

export function getRecords(current, size) {
    return request({
        url: "/vocabulary/getRecords",
        method: "get",
        params: {
            current: current,
            size: size
        }
    });
}

export function add(data) {
    return request({
       url: "/vocabulary/add",
       method: "post",
       data: data,
         headers: {
              "Content-Type": "application/json"
         }
    });
}