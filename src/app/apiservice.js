import axios from "axios";

const httpClient = axios.create({
    baseURL: 'https://lista-presenca-api-deb812641cc5.herokuapp.com'

})

class ApiService {

    constructor(apiurl){
    this.apiurl = apiurl;
    }

    post(url, objeto){
        const requestUrl = `${this.apiurl}${url}`
        return httpClient.post(requestUrl, objeto);
    }

    put(url, objeto){
        const requestUrl = `${this.apiurl}${url}`
        return httpClient.put(requestUrl, objeto);
    }

    get(url){
        const requestUrl = `${this.apiurl}${url}`
        return httpClient.get(requestUrl);
    }
}

export default ApiService;