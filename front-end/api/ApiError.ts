export default class ApiError extends Error {
    private readonly _status: string;
    private readonly _errors: Array<string>;


    constructor(status: string, message: string, errors: Array<string>) {
        super(message);
        this._status = status;
        this._errors = errors;
    }

    get status(): string {
        return this._status;
    }

    get errors(): Array<string> {
        return this._errors;
    }

}