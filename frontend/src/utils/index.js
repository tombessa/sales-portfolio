


export function handleApiErrors(response) {
    if (response.message) throw new Error(response.message);
    return response;
}

export function jumtToTopPage(){
    window.scrollTo(0, 0);
}
