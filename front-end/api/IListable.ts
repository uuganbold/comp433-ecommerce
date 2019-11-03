import IResponse from "./IResponse";

export default interface IListable<Res extends IResponse> {
    list(): Promise<Array<Res>>
}