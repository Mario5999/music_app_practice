import { Component, ElementRef, OnDestroy, ViewChild } from '@angular/core';

@Component({
  selector: 'app-second-child',
  standalone: false,
  templateUrl: './second-child.html',
  styleUrl: './second-child.css'
})
export class SecondChild implements OnDestroy{

  constructor(){
    console.log("COMPONENTE SECOND CHILD CREADO");
  }
  ngOnDestroy(): void {
    console.log("COMPONENTE SECONDCHILD DESTRUIDO");
  }

  @ViewChild("element") element!:ElementRef;

  destroyElement(){
    this.element.nativeElement.classList.remove("viewed");
    this.element.nativeElement.classList.add("destroyed");
  }

}