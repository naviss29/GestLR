import { Pipe, PipeTransform } from '@angular/core';
@Pipe({name: 'extractProperty'})
export class ExtractPropertyPipe implements PipeTransform {
    transform(value: any, selector: string): string {
        if (!selector) {
            throw new Error('missing selector');
        }
        let dot = selector.indexOf('.');
        let context = value;
        let rest = selector;
        while (dot > 0) {
            if (!context) {
                throw new Error('no object defined from which a property could be extracted');
            }
            const beforeDot = rest.substr(0, dot);
            context = context[beforeDot];
            rest = rest.substr(dot + 1);
            dot = rest.indexOf('.');
        }
        if (!context) {
            throw new Error('no object defined from which a property could be extracted');
        }
        return context[rest];
    }
}
