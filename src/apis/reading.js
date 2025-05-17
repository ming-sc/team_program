import request from "@/apis/request";

export function getPractices(current, size, keyword = "") {
    return request({
        url: "/reading/getPractices",
        method: "get",
        params: {
            current: current,
            size: size,
            keyword: keyword
        },
        isLogin: true
    });
}

export function getPractice(readingPracticeId) {
    return request({
        url: "/reading/getPractice",
        method: "get",
        params: {
            readingPracticeId: readingPracticeId
        }
    });
}

export function submit(data) {
    return request({
        url: "/reading/submit",
        method: "post",
        data: data,
        headers: {
            "Content-Type": "application/json"
        }
    });
}

export function getRecords(current, size) {
    return request({
        url: "/reading/getRecords",
        method: "get",
        params: {
            current: current,
            size: size
        }
    });
}

export function getRecord(readingRecordId) {
    return request({
        url: "/reading/getRecord",
        method: "get",
        params: {
            readingRecordId: readingRecordId
        }
    });
}

export function add(title, content, exercises) {
    return request({
        url: "/reading/add",
        method: "post",
        data: {
            title: title,
            content: content,
            exercises: exercises
        },
        headers: {
            "Content-Type": "application/json"
        }
    });
}