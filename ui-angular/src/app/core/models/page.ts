export interface Page<T> {
    content: T[];
    first: boolean;
    last: boolean;
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    sort: string;
    numberOfElements: number;
}
