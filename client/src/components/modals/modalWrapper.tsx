import React, {MouseEventHandler, useCallback, useEffect} from 'react';
import {ModalParams} from "./modalParams";
import cross from "../../assets/images/cross.svg";
import { useRef } from 'react';


const ModalWrapper: React.FC<{ modalParams: ModalParams, children: React.ReactNode }> = ({modalParams, children}) => {
    const {show, onHide} = modalParams
    const modalWrapperRef = useRef<HTMLDivElement | null>(null);
    const modalRef = useRef<HTMLDivElement | null>(null);

    useEffect(() => {
        if (show)
        {
            document.querySelector("body")?.classList.add("modal-open")
            modalWrapperRef.current?.classList.add("active")
            setTimeout(() => {
                modalRef.current?.classList.add("active")
            }, 0)
        } else {
            document.querySelector("body")?.classList.remove("modal-open")
            modalRef.current?.classList.remove("active")
            setTimeout(()=> {
                modalWrapperRef.current?.classList.remove("active")
            }, 400)
        }
    }, [show]);

    const onDivClick = useCallback((event: React.MouseEvent<HTMLDivElement>) => {
        const target = event.target as HTMLElement
        if (target.classList.contains("modal-wrapper") || target.classList.contains("container-4")) {
            onHide()
        }
    }, []);


    return (
        <div className={"modal-wrapper"} onClick={onDivClick} ref={modalWrapperRef}>
            <div className="w-layout-blockcontainer container-4 w-container">
                <div className={"section modal"} ref={modalRef}>
                    <div className="udesly-modal-icons">
                        <div
                            onClick={onHide}
                            className="udesly-button-close w-inline-block">
                            <img
                                src={cross}
                                loading="lazy" alt=""/>
                        </div>
                    </div>
                    {children}
                </div>
            </div>
        </div>
    );
};

export default ModalWrapper;
