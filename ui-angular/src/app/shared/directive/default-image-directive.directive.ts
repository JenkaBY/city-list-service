import {Directive, ElementRef, HostListener} from "@angular/core";

@Directive({
  selector: "img[defaultImage]",
})
export class DefaultImageDirective {
  constructor(private el: ElementRef) {}

  @HostListener("error")
  private onError() {
    this.el.nativeElement.src = "/assets/img/image-not-found.jpg";
  }
}
