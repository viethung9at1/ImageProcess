import React, { Component } from "react";
import "../css/format.css"
export function PreviewImage({ image1, image2 }) {
    return (
        <div className="parent-block">
            <div className="child-block"><img src={image1} className="image" /></div>
            <div className="child-block"><img src={image2}  className="image"/></div>
        </div>
    )
}