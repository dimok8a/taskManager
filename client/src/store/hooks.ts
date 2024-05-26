// src/store/hooks.ts
import { TypedUseSelectorHook, useDispatch, useSelector } from 'react-redux';
import { RootState, AppDispatch } from './index';
import {ChangeEvent, useEffect, useState} from "react";
import {Interface} from "node:readline";

export const useAppDispatch = () => useDispatch<AppDispatch>();
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;


type FormValues = { [key: string]: string };

export const useForm = <T>(initialValues: T) => {
    const [values, setValues] = useState<T>(initialValues);

    const handleChange = (event: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = event.target;
        setValues({
            ...values,
            [name]: value,
        });
    };

    const resetForm = () => {
        setValues(initialValues);
    };

    return { values, handleChange, resetForm, setValues };
};


type FetchFunction<T> = (...arg: any[]) => Promise<T>

type UseFetchReturn<T> = {
    data: T | null;
    loading: boolean;
    error: string | null;
    fetchData: () => Promise<void>;
};

export const useFetch = <T>(fetchFunction: FetchFunction<T>): UseFetchReturn<T> => {
    const [data, setData] = useState<T | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    const fetchData = async () => {
        setLoading(true);
        setError(null);
        try {
            setData(await fetchFunction());
        } catch (err: any) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData()
    }, []);

    return { data, loading, error, fetchData };
};
